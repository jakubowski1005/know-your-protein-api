package com.jakubowskiartur.knowyourprotein.math;

import org.apache.commons.math3.linear.RealVector;
import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;

public class MultiLinearRegression {

    // input

    public double[] doRegression(double[] y, double[][] x) {

        OLSMultipleLinearRegression regression = new OLSMultipleLinearRegression();
        regression.setNoIntercept(true);
        regression.newSampleData(y, x);


        double[] beta = regression.estimateRegressionParameters();

        for (int i = 0; i < beta.length; i++) {
            System.out.println(beta[i]);
        }

        return beta;

    }

    //do regression
}
