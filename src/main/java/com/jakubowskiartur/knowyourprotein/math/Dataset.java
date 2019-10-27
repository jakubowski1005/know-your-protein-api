package com.jakubowskiartur.knowyourprotein.math;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Dataset {

    private double[] x;
    private double[] y;

    public double[][] get2DArray() {

        double[] x = this.getX();
        double[] y = this.getY();

        if(x.length != y.length) throw new IllegalArgumentException("Data series must have the same length.");

        double[][] arr = new double[x.length][2];
        for (int i = 0; i < x.length; i++) {
            arr[i][0] = x[i];
            arr[i][1] = y[i];
        }
        return arr;
    }
}
