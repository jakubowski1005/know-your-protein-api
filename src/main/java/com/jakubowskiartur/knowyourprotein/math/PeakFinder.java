package com.jakubowskiartur.knowyourprotein.math;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class PeakFinder {

    public double[] getSecondaryStructurePeaks(Dataset dataset) {

        double[] wavelengths = dataset.getX();
        double[] minimums = findMinimums(dataset);

        for (int i = 0; i < minimums.length; i++) {
            System.out.println(minimums[i]);
        }

        double[] secondaryStructures = Arrays.stream(minimums).filter(this::isSecondaryStructure).toArray();

        List<Double> absorptionList = new ArrayList<>();

        for (int j = 0; j < secondaryStructures.length; j++) {
            for (int i = 0; i < wavelengths.length; i++) {
                if (secondaryStructures[j] == wavelengths[i]) {
                    absorptionList.add(secondaryStructures[j]);
                }
            }
        }

        return absorptionList.stream().mapToDouble(Double::doubleValue).toArray();
    }

    private boolean isSecondaryStructure(double wavelength) {

        Map<String, Range> map = HardcodedStructures.structures;

        return map.entrySet().stream().anyMatch(
                e -> e.getValue().isInRange(wavelength)
        );
    }

    private double[] findMinimums(Dataset dataset) {

        double[] x = dataset.getX();
        double[] y = dataset.getY();

        List<Double> minValues = new ArrayList<>();

        for (int i = 1; i < x.length-1; i++) {
            if(y[i] < y[i-1] && y[i] < y[i+1]) minValues.add(x[i]);
        }
        return minValues.stream().mapToDouble(Double::doubleValue).toArray();
    }


}
