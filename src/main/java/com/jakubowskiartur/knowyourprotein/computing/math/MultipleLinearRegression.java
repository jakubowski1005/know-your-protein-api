package com.jakubowskiartur.knowyourprotein.computing.math;

import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;

public class MultipleLinearRegression {

    public static double[] getParameters(double[] y, double[][] x) {

        OLSMultipleLinearRegression regression = new OLSMultipleLinearRegression();

        regression.setNoIntercept(true);
        regression.newSampleData(y, x);

        return regression.estimateRegressionParameters();
    }
}
