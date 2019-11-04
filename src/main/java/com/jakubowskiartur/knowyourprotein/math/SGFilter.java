package com.jakubowskiartur.knowyourprotein.math;

public class SGFilter {

    public Dataset smooth(Dataset dataset) {

        double[] x = dataset.getX();
        double[] y = dataset.getY();

        int window = 21;
        double h = 3059;
        int[] coeff = {329, 324, 309, 284, 249, 204, 149, 84, 9, -76, -171};

        int from = (window-1)/2;
        System.out.println(from);
        int to = y.length - from;
        System.out.println(to);

        double[] yprim = y;

        for (int i = from; i < to; i++) {
            yprim[i] = (1/h) * (coeff[10]*y[i-10] + coeff[9]*y[i-9] + coeff[8]*y[i-8] + coeff[7]*y[i-7] + coeff[6]*y[i-6]
                             + coeff[5]*y[i-5] + coeff[4]*y[i-4] + coeff[3]*y[i-3] + coeff[2]*y[i-2] + coeff[1]*y[i-1]
                             + coeff[0]*y[i] + coeff[1]*y[i+1] + coeff[2]*y[i+2] + coeff[3]*y[i+3] + coeff[4]*y[i+4]
                             + coeff[5]*y[i+5] + coeff[6]*y[i+6] + coeff[7]*y[i+7] + coeff[8]*y[i+8] + coeff[9]*y[i+9]
                             + coeff[10]*y[i+10]);
        }

        return new Dataset(x, yprim);
    }
}