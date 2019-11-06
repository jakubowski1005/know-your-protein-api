package com.jakubowskiartur.knowyourprotein.math;

import javax.xml.crypto.Data;
import java.util.Arrays;

public class BaselineCorrector {




    public Dataset substract(Dataset dataset) {

        Dataset background = calculateBackground(dataset);

        double[] y = dataset.getY();
        double[] substracted = new double[y.length];
        double[] yBackground = background.getY();

        for (int i = 0; i < y.length; i++) {
            substracted[i] = y[i] - yBackground[i];
        }

        return Dataset.merge(dataset.getX(), substracted);
    }

    private int findMaximum(double[] values) {

        double value = values[0];
        int index = 0;

        for (int i = 0; i < values.length; i++) {

            if(values[i] > value) {
                value = values[i];
                index = i;
            }
        }

        return index;
    }

    private int findMinimum(double[] values) {

        double value = values[0];
        int index = 0;

        for (int i = 0; i < values.length; i++) {
            if(values[i] < value) {
                value = values[i];
                index = i;
                System.out.println(index);
            }
        }

        return index;
    }

    private Dataset slice(Dataset dataset, int index, String part) {

        double[] wavelengths = dataset.getX();
        double[] values = dataset.getY();

        double[] leftWavelengths = Arrays.copyOfRange(wavelengths, 0, index);
        double[] leftValues = Arrays.copyOfRange(values, 0, index);

        double[] rightWavelengths = Arrays.copyOfRange(wavelengths, index, values.length-1);
        double[] rightValues = Arrays.copyOfRange(values, index, values.length-1);

        if(part.toLowerCase().equals("left")) return Dataset.merge(leftWavelengths, leftValues);
        return Dataset.merge(rightWavelengths, rightValues);
    }

    public Dataset calculateBackground(Dataset dataset) {

        double[] x = dataset.getX();
        double[] y = dataset.getY();

        int maxIndex = findMaximum(y);

        System.out.println("-- MAXIMUM --");
        System.out.println(String.format("x = %f   y = %f", x[maxIndex], y[maxIndex]));

        Dataset leftSide = slice(dataset, maxIndex, "left");
        Dataset rightSide = slice(dataset, maxIndex, "right");

//        for (int i = 0; i < rightSide.getY().length; i++) {
//            System.out.println(rightSide.getX()[i] + "  " + rightSide.getY()[i]);
//        }

        int leftMin = findMinimum(leftSide.getY());
        int rightMin = findMinimum(rightSide.getY());

        System.out.println("--- LEFT SIDE ---");
        System.out.println(String.format("x = %f   y = %f", leftSide.getX()[leftMin], leftSide.getY()[leftMin]));
        System.out.println("--- RIGHT SIDE ---");
        System.out.println(String.format("x = %f   y = %f", rightSide.getX()[rightMin], rightSide.getY()[rightMin]));

        double a = (leftSide.getY()[leftMin] - rightSide.getY()[rightMin])/(leftSide.getX()[leftMin] - rightSide.getX()[rightMin]);
        double b = leftSide.getY()[leftMin] - (a * leftSide.getX()[leftMin]);

        System.out.println(String.format("a: %f    b: %f", a, b));

        double[] yBackground = new double[y.length];

        for (int i = 0; i < y.length; i++) {
            yBackground[i] = a * x[i] + b;
            if (yBackground[i] < 0) yBackground[i] = 0;
        }

        return Dataset.merge(x, yBackground);
    }





























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
