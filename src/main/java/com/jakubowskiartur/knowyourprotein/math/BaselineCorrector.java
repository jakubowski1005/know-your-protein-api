package com.jakubowskiartur.knowyourprotein.math;

import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
class BaselineCorrector {

    Dataset subtract(Dataset dataset) {

        Dataset background = calculateBackground(dataset);

        double[] y = dataset.getY();
        double[] yBackground = background.getY();

        for (int i = 0; i < y.length; i++) {
            y[i] -= yBackground[i];
        }

        return Dataset.merge(dataset.getX(), y);
    }

    private Dataset calculateBackground(Dataset dataset) {

        double[] x = dataset.getX();
        double[] y = dataset.getY();

        int maxIndex = findMaximum(y);

        Dataset leftSide = slice(dataset, maxIndex, "left");
        Dataset rightSide = slice(dataset, maxIndex, "right");

        int leftMin = findMinimum(leftSide.getY());
        int rightMin = findMinimum(rightSide.getY());

        double a = (leftSide.getY()[leftMin] - rightSide.getY()[rightMin])/(leftSide.getX()[leftMin] - rightSide.getX()[rightMin]);
        double b = leftSide.getY()[leftMin] - (a * leftSide.getX()[leftMin]);

        double[] yBackground = new double[y.length];

        for (int i = 0; i < y.length; i++) {
            yBackground[i] = a * x[i] + b;
            if (yBackground[i] < 0) yBackground[i] = 0;
        }

        return Dataset.merge(x, yBackground);
    }

    private int findMaximum(double[] values) {

        double value = values[0];
        int index = 0;

        for (int i = 0; i < values.length; i++) {
            if(values[i] > value) {
                value = values[i];
                index = i;
            }
        }
        return index;
    }

    private int findMinimum(double[] values) {

        double value = values[0];
        int index = 0;

        for (int i = 0; i < values.length; i++) {
            if(values[i] < value) {
                value = values[i];
                index = i;
            }
        }
        return index;
    }

    private Dataset slice(Dataset dataset, int index, String part) {

        double[] wavelengths = dataset.getX();
        double[] values = dataset.getY();

        double[] xLeft = Arrays.copyOfRange(wavelengths, 0, index);
        double[] yLeft = Arrays.copyOfRange(values, 0, index);

        double[] xRight = Arrays.copyOfRange(wavelengths, index, values.length-1);
        double[] yRight = Arrays.copyOfRange(values, index, values.length-1);

        if(part.toLowerCase().equals("left")) return Dataset.merge(xLeft, yLeft);
        return Dataset.merge(xRight, yRight);
    }
}