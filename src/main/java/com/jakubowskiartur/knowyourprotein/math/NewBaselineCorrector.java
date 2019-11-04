package com.jakubowskiartur.knowyourprotein.math;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class NewBaselineCorrector {

    public Dataset correct(Dataset dataset) {


        // find maximum

        // slice array into two

        // find minimum of each

        double[] x = dataset.getX();
        double[] y = dataset.getY();

        double maximum = 0;
        int maximumIndex = 0;

        for (int i = 0; i < x.length; i++) {
            if(y[i] > maximum) {
                maximum = y[i];
                maximumIndex = i;
            }
        }

        List<List<Double>> xx = sliceArray(x, maximumIndex);
        List<List<Double>> yy = sliceArray(y, maximumIndex);

//        for (List<Double> list: yy) {
//            System.out.println("New list:");
//            for (Double d: list) {
//                System.out.println(d);
//            }
//        }

        double leftMin = 100;
        double rightMin = 100;

        int leftXIndex = 0;
        int rightXIndex = 0;

        double[] leftSide = listToArr(yy.get(0));
        double[] rightSide = listToArr(yy.get(1));

        for (int i = 0; i < leftSide.length; i++) {
            System.out.println(leftSide);
        }


        for (int i = 0; i < leftSide.length; i++) {
            if(leftSide[i] < leftMin) {
                leftMin = leftSide[i];
                leftXIndex = i;
            }
        }


        for (int i = 0; i < rightSide.length; i++) {
            if(rightSide[i] < rightMin) {
                rightMin = rightSide[i];
                rightXIndex = i;
            }
        }

        double[] yyyy = new double[y.length];

        int n = rightXIndex - leftXIndex;

        System.out.println(n);

        if(leftMin < rightMin) {

            double diff = Math.abs(leftMin - rightMin);

            double step = diff/n;




            for (int i = 0; i < y.length; i++) {
                yyyy[i] = y[i] - step;
                step += step;
            }

        }

        if(rightMin < leftMin) {

            double diff = Math.abs(leftMin - rightMin);

            double step = diff/n;

            for (int i = y.length-1; i >= 0; i--) {
                yyyy[i] = y[i] - step;
                step += step;
            }

        }

        return new Dataset(x, yyyy);


    }

    private double[] listToArr(List<Double> list) {
        return list.stream().mapToDouble(Double::doubleValue).toArray();
    }

    private List<List<Double>> sliceArray(double[] arr, int index) {

        List<List<Double>> finalList = new ArrayList<>();

        List<Double> list = Arrays.stream(arr)
                .boxed()
                .collect(Collectors.toList());



        List<Double> firstList = list.subList(0, index);
        List<Double> secondList = list.subList(index+1, list.size()-1);

        finalList.add(firstList);
        finalList.add(secondList);

        return finalList;
    }
}
