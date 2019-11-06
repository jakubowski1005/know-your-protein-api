package com.jakubowskiartur.knowyourprotein.math;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;

public class IOTester {

    // get data from file and return dataset object

    public Dataset CSVToArray(String path) {
        double[][] dataAsDoubleArray = null;
        try {
            dataAsDoubleArray =
                    Files.lines(Paths.get(path))
                            .map(s -> s.split(","))
                            .map(s -> Arrays.stream(s).mapToDouble(Double::parseDouble).toArray())
                            .toArray(double[][]::new);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arrToDataset(dataAsDoubleArray);
    }

    //dataset creator method

    private Dataset arrToDataset(double[][] data) {

        double[] x = new double[data.length];
        double[] y = new double[data.length];

        for (int i = 0; i < data.length; i++) {
            x[i] = data[i][0];
            y[i] = data[i][1];
        }
        return Dataset.merge(x,y);
    }

    // convert dataset to csv file

    public void convertToCSV(double[][] data, String filename) throws IOException {

        String[][] strArr = doubleToStr2DArray(data);

        try {
            BufferedWriter br = new BufferedWriter(new FileWriter("/Users/arturjakubowski/Desktop/" + filename + ".CSV"));
            StringBuilder sb = new StringBuilder();
            for (String[] element : strArr) {
                sb.append(element[0]);
                sb.append(",");
                sb.append(element[1]);
                sb.append("\n");
            }

            br.write(sb.toString());
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String[][] doubleToStr2DArray(double[][] arr) {
        String[][] newArr = new String[arr.length][2];

        for (int i = 0; i < arr.length; i++) {
            newArr[i][0] = String.valueOf(arr[i][0]);
            newArr[i][1] = String.valueOf(arr[i][1]);
        }
        return newArr;
    }

    // main

    public static void main(String[] args) throws IOException {

        IOTester io = new IOTester();

        String path = "/Users/arturjakubowski/Desktop/B1.CSV";

        Dataset data = io.CSVToArray(path);

        Dataset smothedSpectra = new SGFilter().smooth(data);
//        io.convertToCSV(smothedSpectra.get2DArray(), "prezka/smoothed");

        Dataset amide = BandSlicer.slice(smothedSpectra, 1600, 1700);


        Dataset substracted = new BaselineCorrector().subtract(amide);

        io.convertToCSV(substracted.get2DArray(), "prezka/correction");

//        io.convertToCSV(amide.get2DArray(), "prezka/amide");

//        Dataset diff = Differentiation.diff(amide);
//        Dataset diff2 = Differentiation.diff(diff);
//        io.convertToCSV(diff2.get2DArray(), "prezka/diff2");

//        double[] secondary = new PeakFinder().getSecondaryStructurePeaks(diff2);
//
//        Map<String, Double> mapp = new PeakFinder().getMeanPeakValues(secondary);
//
//        for (Map.Entry item: mapp.entrySet()) {
//            System.out.println(item.getKey() + "    " + item.getValue());
//        }
       // Dataset baseline = new SGFilter().smooth(amide);//new BaselineCorrector().correctBaseline(amide);



//        System.out.println("X           sub         amide");
//        for (int i = 0; i < baseline.getX().length; i++) {
//            if(baseline.getY()[i] != amide.getY()[i]) System.out.println("NOT EQUAL");
//            System.out.println(baseline.getX()[i] + "       " + baseline.getY()[i] + "      " + amide.getY()[i]);
//        }

        //io.convertToCSV(baseline.get2DArray(), "baseline");

       // Dataset substracted = new NewBaselineCorrector().correct(amide);

//        for (int i = 0; i < substracted.getX().length; i++) {
//            //if(baseline.getY()[i] != amide.getY()[i]) System.out.println("NOT EQUAL");
//            System.out.println(substracted.getX()[i] + "       " + substracted.getY()[i]); //+ "      " + amide.getY()[i]);
//        }

     //  io.convertToCSV(substracted.get2DArray(), "substractedPlease");


    }
}
