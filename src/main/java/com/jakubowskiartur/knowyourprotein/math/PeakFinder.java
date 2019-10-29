package com.jakubowskiartur.knowyourprotein.math;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class PeakFinder {

    public Dataset getSecondaryStructurePeaks(Dataset dataset) {

        double[] wavelengths = dataset.getX();
        double[] minimums = findMinimums(dataset);
        double[] secondaryStructures = Arrays.stream(minimums).filter(this::isSecondaryStructure).toArray();

        List<Double> absorptionList = new ArrayList<>();

        for (int i = 0; i < wavelengths.length; i++) {
            if(secondaryStructures[i] == wavelengths[i]) {
                absorptionList.add(secondaryStructures[i]);
            }
        }

        double[] absorption = absorptionList.stream().mapToDouble(Double::doubleValue).toArray();
        return new Dataset(secondaryStructures, absorption);
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
