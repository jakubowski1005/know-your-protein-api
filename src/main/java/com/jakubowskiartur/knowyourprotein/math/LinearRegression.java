package com.jakubowskiartur.knowyourprotein.math;

public class LinearRegression {

    public static double calculateSlope(Dataset dataset) {

        double[] x = dataset.getX();
        double[] y = dataset.getY();
        int n = x.length;
        double sumX = 0;
        double sumY = 0;
        double xxBar = 0;
        double xyBar = 0;

        for (int i = 0; i < n; i++) {
            sumX  += x[i];
            sumY  += y[i];
        }

        double xBar = sumX / n;
        double yBar = sumY / n;

        for (int i = 0; i < n; i++) {
            xxBar += (x[i] - xBar) * (x[i] - xBar);
            xyBar += (x[i] - xBar) * (y[i] - yBar);
        }
        return xyBar / xxBar;
    }
}
