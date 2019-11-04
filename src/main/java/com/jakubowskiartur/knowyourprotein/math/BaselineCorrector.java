package com.jakubowskiartur.knowyourprotein.math;

public class BaselineCorrector {


    public Dataset simpleCorrector(Dataset dataset) {

        return null;
    }






    public Dataset correctBaseline(Dataset dataset) {

        double[] x = dataset.getX();
        double[] y = dataset.getY();

        double[] yprim = new double[y.length];

        yprim[0] = y[0];
        yprim[y.length-1] = y[y.length-1];


        double sabs = 0;

        double delta;

        double oldSabs = 0;

        int counter = 0;

        do {

            counter++;


            for (int i = 1; i < y.length-1; i++) {

                yprim[i] = Math.min(y[i], (y[i-1]+y[i+1])/2);
            }

            for (int i = 0; i < y.length; i++) {
                sabs += Math.abs(y[i] - yprim[i]);
            }

            delta = Math.abs(sabs - oldSabs);

            oldSabs = sabs;

            y = yprim;

            //System.out.println(counter);

        } while (counter == 100_000_000);//delta < 0.0021);

        return new Dataset(x, y);
    }

    public Dataset getBaseline(Dataset dataset) {
        return null;
    }
}
