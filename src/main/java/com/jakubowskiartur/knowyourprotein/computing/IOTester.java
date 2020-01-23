package com.jakubowskiartur.knowyourprotein.computing;


import com.jakubowskiartur.knowyourprotein.computing.analyzing.DataValidator;
import com.jakubowskiartur.knowyourprotein.computing.analyzing.Deconvolution;
import com.jakubowskiartur.knowyourprotein.computing.analyzing.PeakFinder;
import com.jakubowskiartur.knowyourprotein.computing.analyzing.ResponseCreator;
import com.jakubowskiartur.knowyourprotein.computing.math.BandSlicer;
import com.jakubowskiartur.knowyourprotein.computing.math.Differentiation;
import com.jakubowskiartur.knowyourprotein.computing.pojos.Dataset;
import com.jakubowskiartur.knowyourprotein.computing.pojos.StructureModel;
import com.jakubowskiartur.knowyourprotein.computing.quality.*;
import com.jakubowskiartur.knowyourprotein.pojos.SpectrumData;
//import com.jakubowskiartur.knowyourprotein.services.SpectrumDataService;

import javax.xml.crypto.Data;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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


    public void createCSV2(List<StructureModel> data, String filename) throws IOException {

        double[] wavelengths = data.get(data.size()-1).getData().getX();
        double[] original = data.get(data.size()-1).getData().getY();

        List<double[]> components = new ArrayList<>();
        for (int i = 0; i < data.size()-1; i++) {
            components.add(data.get(i).getData().getY());
        }

        try {
            BufferedWriter br = new BufferedWriter(new FileWriter("/Users/arturjakubowski/Desktop/OSTATNIA/" + filename + ".CSV"));
            StringBuilder sb = new StringBuilder();
            for (int i=0; i < wavelengths.length; i++) {

                sb.append(wavelengths[i]);
                sb.append(",");
                sb.append(original[i]);
                sb.append(",");

                for(int j = 0; j < components.size(); j++) {
                    sb.append(components.get(j)[i]);
                    sb.append(",");
                }

                sb.append("\n");
            }
            br.write(sb.toString());
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void createCSV(List<Dataset> data, String filename) throws IOException {

        double[] wavelengths = data.get(data.size()-1).getX();
        double[] original = data.get(data.size()-1).getY();

        List<double[]> components = new ArrayList<>();
        for (int i = 0; i < data.size()-1; i++) {
            components.add(data.get(i).getY());
        }

        try {
            BufferedWriter br = new BufferedWriter(new FileWriter("/Users/arturjakubowski/Desktop/WIDMA/WIDMO_PIERWSZE/" + filename + ".CSV"));
            StringBuilder sb = new StringBuilder();
            for (int i=0; i < wavelengths.length; i++) {

                sb.append(wavelengths[i]);
                sb.append(",");
                sb.append(original[i]);
                sb.append(",");

                for(int j = 0; j < components.size(); j++) {
                    sb.append(components.get(j)[i]);
                    sb.append(",");
                }

                sb.append("\n");
            }
            br.write(sb.toString());
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void convertToCSV(double[][] data, String filename) throws IOException {

        String[][] strArr = doubleToStr2DArray(data);

        try {
            BufferedWriter br = new BufferedWriter(new FileWriter("/Users/arturjakubowski/Desktop/WIDMA/WIDMO_PIERWSZE/" + filename + ".CSV"));
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

    public void printPeaks(Map<String, Double> peaks) {
        System.out.println("Name" + "   " + "Value");
        for (Map.Entry peak: peaks.entrySet()) {
            System.out.println(peak.getKey() + "    " + peak.getValue());
        }
    }

    public void manualAnalysis(Dataset dataset) throws IOException {

        IOTester io = new IOTester();
        Deconvolution deconvolution = new Deconvolution(new PeakFinder());

        io.convertToCSV(dataset.get2DArray(), "surowy");

        Dataset smoothedSpectra = new SGFilter().smooth(dataset);
        io.convertToCSV(smoothedSpectra.get2DArray(), "wygladzony");

        Dataset amide = BandSlicer.slice(smoothedSpectra, 1600, 1700);
        io.convertToCSV(amide.get2DArray(), "amid");

        Dataset subtracted = new BaselineCorrector().subtract(amide);
        io.convertToCSV(subtracted.get2DArray(), "korekcja");

        Dataset diff = Differentiation.diff(subtracted, 4);
        io.convertToCSV(diff.get2DArray(), "diff");

        Map<String, Double> peaks = new PeakFinder().getSecondaryStructures(subtracted);
        io.printPeaks(peaks);

        List<StructureModel> deconvolved = deconvolution.deconvolve(subtracted);
        io.createCSV2(deconvolved, "final");

    }


    public static void main(String[] args) throws IOException {


        IOTester io = new IOTester();
        Deconvolution deconvolution = new Deconvolution(new PeakFinder());

        String path1 = "/Users/arturjakubowski/Desktop/B1.CSV";
        String path2 = "/Users/arturjakubowski/Desktop/Inżynierka - podsumowanie/dane_do_rozkladow/peptydyR1-R5/means_R1.csv";

        Dataset first = io.CSVToArray(path1);
        //Dataset second = io.CSVToArray(path2);

        //io.convertToCSV(dataset.get2DArray(), "surowy");

        Dataset smoothedSpectra = new SGFilter().smooth(first);
        //Dataset smoothedSpectra2 = new SGFilter().smooth(second);
        //io.convertToCSV(smoothedSpectra.get2DArray(), "wygladzony");

        Dataset amide = BandSlicer.slice(smoothedSpectra, 1600, 1700);
        //Dataset amide2 = BandSlicer.slice(smoothedSpectra2, 1600, 1700);
        //io.convertToCSV(amide.get2DArray(), "amid");

        Dataset subtracted = new BaselineCorrector().subtract(amide);
        //Dataset subtracted2 = new BaselineCorrector().subtract(amide2);
        //io.convertToCSV(subtracted.get2DArray(), "korekcja");

        //Dataset diff = Differentiation.diff(subtracted, 4);
        //io.convertToCSV(diff.get2DArray(), "diff");

        //Map<String, Double> peaks = new PeakFinder().getSecondaryStructures(subtracted);
        //io.printPeaks(peaks);

        List<StructureModel> deconvolved = deconvolution.deconvolve(subtracted);
        //List<StructureModel> deconvolved2 = deconvolution.deconvolve(subtracted2);
        io.createCSV2(deconvolved, "final1");
        //io.createCSV2(deconvolved, "final2");


        ////REDUNDANT////
        /////////////////////
//        double[] peaksPositions = peaks.values().stream().mapToDouble(Double::doubleValue).toArray();
//
//        List<Dataset> gaussianCurves = new ArrayList<>();
//
//        for (double peaksPosition : peaksPositions) {
//            gaussianCurves.add(deconvolution.calculateGaussianCurve(subtracted, peaksPosition));
//        }
//        gaussianCurves.add(subtracted);
//        io.createCSV(gaussianCurves, "AMP1");
//
//        System.out.println();
//        System.out.println("Amplitudes:");
//
//        double[] amplitudes = deconvolution.calculateAmplitude(subtracted, peaksPositions);
//        for(double d: amplitudes) {
//            System.out.println(d);
//        }
        //////////////////////

    }
}
