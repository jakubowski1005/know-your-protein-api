package com.jakubowskiartur.knowyourprotein.math;

import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;

class MultipleLinearRegression {

    static double[] getParameters(double[] y, double[][] x) {

        OLSMultipleLinearRegression regression = new OLSMultipleLinearRegression();

        regression.setNoIntercept(true);
        regression.newSampleData(y, x);

        return regression.estimateRegressionParameters();
    }
}
