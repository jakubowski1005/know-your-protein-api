package com.jakubowskiartur.knowyourprotein.math;

import java.util.Collections;
import java.util.List;

public class Deconvolution {

    public List<Dataset> deconvolute(Dataset dataset) {




        return Collections.emptyList();
    }



    public Dataset gaussianFit(Dataset dataset, double peakPosition, double coeff) {

        Dataset gaussian = calculateGaussianCurve(dataset, peakPosition);
        double amplitude = coeff;//calculateAmplitude(Dataset.merge(gaussian.getY(), dataset.getY()));

        System.out.println(String.format("Peak: %f      Amplitude: %f", peakPosition, amplitude));

        double[] x = gaussian.getX();
        double[] y = gaussian.getY();

        for (int i = 0; i < y.length; i++) {
            y[i] *= Math.abs(amplitude);
        }
        return Dataset.merge(x, y);
    }

    public Dataset calculateGaussianCurve(Dataset dataset, double peakPosition) {

        double[] x = dataset.getX();
        double[] y = new double[x.length];
        double width = 20;

        for (int i = 0; i < x.length; i++) {
            y[i] = Math.exp((-1) * Math.pow(((x[i] - peakPosition)/(.6006 * width)), 2));
        }
        return Dataset.merge(x, y);
    }

    public double calculateAmplitude(Dataset dataset) {
        return LinearRegression.calculateSlope(dataset);
    }


}
