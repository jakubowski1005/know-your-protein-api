package com.jakubowskiartur.knowyourprotein.computing.math;

import com.jakubowskiartur.knowyourprotein.computing.pojos.Dataset;

public class Integration {

    public static double integrate(Dataset dataset) {

        double[] y = dataset.getY();
        double area = 0;

        for (int i = 0; i < y.length-1; i++) {
            area +=  (y[i] + y[i+1]) / 2;
        }
        return area;
    }
}