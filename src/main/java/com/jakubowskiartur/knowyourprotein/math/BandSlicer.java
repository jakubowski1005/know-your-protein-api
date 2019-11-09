package com.jakubowskiartur.knowyourprotein.math;

import java.util.Arrays;

class BandSlicer {

    static Dataset slice(Dataset dataset, double from, double to) {

        double[] wavelengths = dataset.getX();
        double[] values = dataset.getY();

        int firstIndex = (Arrays.binarySearch(wavelengths, from) + 1) * (-1);
        int lastIndex = (Arrays.binarySearch(wavelengths, to) + 1) * (-1);

        double[] amideWavelengths = Arrays.copyOfRange(wavelengths, firstIndex, lastIndex);
        double[] amideValues = Arrays.copyOfRange(values, firstIndex, lastIndex);

        return Dataset.merge(amideWavelengths, amideValues);
    }
}