package com.jakubowskiartur.knowyourprotein.math;

import java.util.Collections;
import java.util.List;

public class Deconvolution {

    public List<Dataset> deconvolute(Dataset dataset) {
        return Collections.emptyList();
    }

    // linearRegression(data, coef)

    private Dataset gaussianFit(Dataset dataset, double peakPosition) {

        double[] x = dataset.getX();
        double[] y = dataset.getY();
        return dataset;
    }

    public Dataset calculateGaussianCurve(Dataset dataset, double peakPosition) {

        double[] x = dataset.getX();
        double[] y = dataset.getY();
        double width = 20;

        for (int i = 0; i < x.length; i++) {
            x[i] = Math.exp(-1 * Math.pow(((x[i] - peakPosition)/(.6006 * width)), 2));
        }
        return Dataset.merge(x, y);
    }

    public double calculateAmplitude(Dataset dataset) {
        return LinearRegression.calculateSlope(dataset);
    }


}
