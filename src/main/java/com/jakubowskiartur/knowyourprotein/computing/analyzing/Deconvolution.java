package com.jakubowskiartur.knowyourprotein.computing.analyzing;

import com.jakubowskiartur.knowyourprotein.computing.math.Integration;
import com.jakubowskiartur.knowyourprotein.computing.math.MultipleLinearRegression;
import com.jakubowskiartur.knowyourprotein.computing.pojos.Dataset;
import com.jakubowskiartur.knowyourprotein.computing.pojos.StructureModel;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

@Service
public class Deconvolution {

    private PeakFinder peakFinder;

    @Inject
    public Deconvolution(PeakFinder peakFinder) {
        this.peakFinder = peakFinder;
    }

    public List<StructureModel> deconvolve(Dataset dataset) {

        Map<String, Double> structures = peakFinder.getSecondaryStructures(dataset);
        List<StructureModel> structureModels = new ArrayList<>();

        double[] peaksPositions = structures.values().stream().mapToDouble(Double::doubleValue).toArray();
        String[] names = structures.keySet().toArray(new String[0]);
        double[] amplitudes = calculateAmplitude(dataset, peaksPositions);

        structureModels.add(new StructureModel("Amide I", dataset, Integration.integrate(dataset)));

        for (int i = 0; i < names.length; i++) {
            Dataset component = gaussianFit(dataset, peaksPositions[i], amplitudes[i]);
            structureModels.add(
                    new StructureModel(names[i], component, Integration.integrate(component))
            );
        }
        return structureModels;
    }

    private Dataset gaussianFit(Dataset dataset, double peakPosition, double coeff) {

        Dataset gaussian = calculateGaussianCurve(dataset, peakPosition);

        double[] x = gaussian.getX();
        double[] y = gaussian.getY();

        for (int i = 0; i < y.length; i++) {
            y[i] *= coeff;
        }
        return Dataset.merge(x, y);
    }

    private Dataset calculateGaussianCurve(Dataset dataset, double peakPosition) {

        double[] x = dataset.getX();
        double[] y = new double[x.length];
        double width = 20;

        for (int i = 0; i < x.length; i++) {
            y[i] = Math.exp((-1) * Math.pow(((x[i] - peakPosition)/(.6006 * width)), 2));
        }
        return Dataset.merge(x, y);
    }

    private double[] calculateAmplitude(Dataset dataset, double[] peakPositions) {

        List<List<Double>> gaussian = new ArrayList<>();

        for (double peak : peakPositions) {
            gaussian.add(DoubleStream.of(calculateGaussianCurve(dataset, peak).getY())
                    .boxed().collect(Collectors.toList()));
        }

        Double[][] components = gaussian.stream().map(l -> l.toArray(new Double[0])).toArray(Double[][]::new);

        return MultipleLinearRegression.getParameters(dataset.getY(), rotate(toPrimitive(components)));
    }

    private double[][] rotate(double[][] array) {
        double[][] newArray = new double[array[0].length][array.length];
        for (int i = 0; i < array[0].length; i++) {
            for (int j = 0; j < array.length; j++) {
                newArray[i][j] = array[j][i];
            }
        }
        return newArray;
    }

    private double[][] toPrimitive(Double[][] array) {
        double[][] primitives = new double[array.length][array[0].length];

        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                primitives[i][j] = array[i][j];
            }
        }
        return primitives;
    }
}