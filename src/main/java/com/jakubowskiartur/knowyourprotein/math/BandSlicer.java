package com.jakubowskiartur.knowyourprotein.math;

import java.util.Arrays;

public class BandSlicer {

    public static double[][] slice(double[][] data, double from, double to) {

        double[] wavelengths = split(data,0);
        double[] values = split(data,1);

        int firstIndex = (Arrays.binarySearch(wavelengths, from) + 1) * (-1);
        int lastIndex = (Arrays.binarySearch(wavelengths, to) + 1) * (-1);

        double[] amideWavelengths = Arrays.copyOfRange(wavelengths, firstIndex, lastIndex);
        double[] amideValues = Arrays.copyOfRange(values, firstIndex, lastIndex);

        return merge(amideWavelengths, amideValues);
    }

    private static double[] split(double[][] arr, int column) {
        if(column > 1 || column < 0) throw new IllegalArgumentException("This value must be equal 0 or 1");

        double[] newArr = new double[arr.length];

        for (int i = 0; i < newArr.length; i++) {
            newArr[i] = arr[i][column];
        }
        return newArr;
    }

    private static double[][] merge(double[] firstArr, double[] secondArr) {
        if(firstArr.length != secondArr.length) throw new IllegalArgumentException("Arrays must have the same length");

        double[][] newArr = new double[firstArr.length][2];

        for (int i = 0; i < firstArr.length; i++) {
            newArr[i][0] = firstArr[i];
            newArr[i][1] = secondArr[i];
        }
        return newArr;
    }
}
