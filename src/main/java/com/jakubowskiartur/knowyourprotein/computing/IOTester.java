package com.jakubowskiartur.knowyourprotein.computing;

import com.jakubowskiartur.knowyourprotein.computing.analyzing.DataValidator;
import com.jakubowskiartur.knowyourprotein.computing.analyzing.Deconvolution;
import com.jakubowskiartur.knowyourprotein.computing.analyzing.PeakFinder;
import com.jakubowskiartur.knowyourprotein.computing.analyzing.ResponseCreator;
import com.jakubowskiartur.knowyourprotein.computing.pojos.Dataset;
import com.jakubowskiartur.knowyourprotein.computing.pojos.StructureModel;
import com.jakubowskiartur.knowyourprotein.computing.quality.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

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


    public void createCSV(List<StructureModel> data, String filename) throws IOException {

        double[] wavelengths = data.get(0).getData().getX();
        double[] original = data.get(0).getData().getY();
        double[] component1 = data.get(1).getData().getY();
        double[] component2 = data.get(2).getData().getY();
        double[] component3 = data.get(3).getData().getY();
        double[] component4 = data.get(4).getData().getY();
        double[] component5 = data.get(5).getData().getY();
        double[] component6 = data.get(6).getData().getY();

        try {
            BufferedWriter br = new BufferedWriter(new FileWriter("/Users/arturjakubowski/Desktop/" + filename + ".CSV"));
            StringBuilder sb = new StringBuilder();
            for (int i=0; i < wavelengths.length; i++) {
                sb.append(wavelengths[i]);
                sb.append(",");
                sb.append(original[i]);
                sb.append(",");
                sb.append(component1[i]);
                sb.append(",");
                sb.append(component2[i]);
                sb.append(",");
                sb.append(component3[i]);
                sb.append(",");
                sb.append(component4[i]);
                sb.append(",");
                sb.append(component5[i]);
                sb.append(",");
                sb.append(component6[i]);
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

//    private static SpectrumAnalyzer analyzer;
//
//    @Inject
//    public IOTester(SpectrumAnalyzer analyzer) {
//        this.analyzer = analyzer;
//    }

    public static void main(String[] args) throws IOException {

        IOTester io = new IOTester();

        String path = "/Users/arturjakubowski/Desktop/B1.CSV";

        Dataset data = io.CSVToArray(path);

        List<StructureModel> list = new SpectrumAnalyzer(new DataValidator(), new Deconvolution(new PeakFinder()), new ResponseCreator(), new QualityEnhancer(new SGFilter(), new BaselineCorrector())).analyzeSpectrum(data);

        io.createCSV(list, "prezka/nowedane");

//        Dataset smothedSpectra = new SGFilter().smooth(data);
//        io.convertToCSV(smothedSpectra.get2DArray(), "prezka/smoothed");

//        Dataset amide = BandSlicer.slice(smothedSpectra, 1600, 1700);


//        Dataset substracted = new BaselineCorrector().subtract(amide);


//        Dataset diff = Differentiation.diff(substracted);
//        Dataset diff2 = Differentiation.diff(diff);


//        double[] secondary = new PeakFinder().getSecondaryStructurePeaks(diff2);
//
//        double[] peaks = new PeakFinder().getMeanPeakValues(secondary);

//        Deconvolution deconvolution = new Deconvolution();

//        Dataset component1Gauss = deconvolution.calculateGaussianCurve(substracted, peaks[0]);
//        Dataset component2Gauss = deconvolution.calculateGaussianCurve(substracted, peaks[1]);
//        Dataset component3Gauss = deconvolution.calculateGaussianCurve(substracted, peaks[2]);
//        Dataset component4Gauss = deconvolution.calculateGaussianCurve(substracted, peaks[3]);
//        Dataset component5Gauss = deconvolution.calculateGaussianCurve(substracted, peaks[4]);
//        Dataset component6Gauss = deconvolution.calculateGaussianCurve(substracted, peaks[5]);

//        double[][] x = new double[substracted.getX().length][6];
//
//        for (int i = 0; i < x.length; i++) {
//            x[i][0] = component1Gauss.getY()[i];
//            x[i][1] = component2Gauss.getY()[i];
//            x[i][2] = component3Gauss.getY()[i];
//            x[i][3] = component4Gauss.getY()[i];
//            x[i][4] = component5Gauss.getY()[i];
//            x[i][5] = component6Gauss.getY()[i];
//        }
//
//        double[] coeffs = new MultiLinearRegression().doRegression(substracted.getY(), x);




//        List<Dataset> components = new ArrayList<>();
//        components.add(substracted);
//        components.add(component1Gauss);
//        components.add(component2Gauss);
//        components.add(component3Gauss);
//        components.add(component4Gauss);
//        components.add(component5Gauss);
//        components.add(component6Gauss);

//        for (int i = 0; i < peaks.length; i++) {
//            components.add(deconvolution.gaussianFit(substracted, peaks[i], coeffs[i]));
//        }


//        io.createCSV(components, "prezka/dane");

//        Dataset component = new Deconvolution().gaussianFit(substracted, 1621);
//
//        io.convertToCSV(component.get2DArray(), "prezka/component1");


//        io.convertToCSV(substracted.get2DArray(), "prezka/correction");

//        io.convertToCSV(amide.get2DArray(), "prezka/amide");


//        io.convertToCSV(diff2.get2DArray(), "prezka/diff2");


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
