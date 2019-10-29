package com.jakubowskiartur.knowyourprotein.math;

public class Integration {

    static double integrate(Dataset dataset) {

        double[] y = dataset.getY();
        double area = 0;

        for (int i = 0; i < y.length-1; i++) {
            area +=  (y[i] + y[i+1]) / 2;
        }
        return area;
    }
}