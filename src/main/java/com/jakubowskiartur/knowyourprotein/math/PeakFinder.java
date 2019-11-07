package com.jakubowskiartur.knowyourprotein.math;

import java.util.*;

public class PeakFinder {

    public double[] getMeanPeakValues(double[] peaks) {

        Map<String, Double> values = new HashMap<>();

        double aggregatedStrands = 0;
        int aggregatedStrandsCounter = 0;
        double betaSheet = 0;
        int betaSheetCounter = 0;
        double unordered = 0;
        int unorderedCounter = 0;
        double alphaHelix = 0;
        int alphaHelixCounter = 0;
        double helix310 = 0;
        int helix310Counter = 0;
        double antiparallelBetaSheet = 0;
        int antiparallelBetaSheetCounter = 0;

        for (int i = 0; i < peaks.length; i++) {
            if (peaks[i] > 1610 && peaks[i] < 1628) {
                aggregatedStrands += peaks[i];
                aggregatedStrandsCounter++;
            }
            if (peaks[i] > 1629 && peaks[i] < 1640) {
                betaSheet += peaks[i];
                betaSheetCounter++;
            }
            if (peaks[i] > 1641 && peaks[i] < 1648) {
                unordered += peaks[i];
                unorderedCounter++;
            }
            if (peaks[i] > 1649 && peaks[i] < 1660) {
                alphaHelix += peaks[i];
                alphaHelixCounter++;
            }
            if (peaks[i] > 1661 && peaks[i] < 1670) {
                helix310 += peaks[i];
                helix310Counter++;
            }
            if (peaks[i] > 1675 && peaks[i] < 1695) {
                antiparallelBetaSheet += peaks[i];
                antiparallelBetaSheetCounter++;
            }
        }

        values.put("aggregatedStrands", aggregatedStrands/aggregatedStrandsCounter);
        values.put("betaSheet", betaSheet/betaSheetCounter);
        values.put("unordered", unordered/unorderedCounter);
        values.put("alphaHelix", alphaHelix/alphaHelixCounter);
        values.put("helix310", helix310/helix310Counter);
        values.put("antiparallelBetaSheet", antiparallelBetaSheet/antiparallelBetaSheetCounter);

        double[] peakss =  values.values().stream().mapToDouble(Double::doubleValue).toArray();

        for (int i = 0; i < peakss.length; i++) {
            System.out.println(peakss[i]);
        }
        return peakss;
    }

    public double[] getSecondaryStructurePeaks(Dataset dataset) {

        double[] wavelengths = dataset.getX();
        double[] minimums = findMinimums(dataset);
        double[] secondaryStructures = Arrays.stream(minimums).filter(this::isSecondaryStructure).toArray();

        List<Double> absorptionList = new ArrayList<>();

        for (int j = 0; j < secondaryStructures.length; j++) {
            for (int i = 0; i < wavelengths.length; i++) {
                if (secondaryStructures[j] == wavelengths[i]) {
                    absorptionList.add(secondaryStructures[j]);
                }
            }
        }

        double[] arr = absorptionList.stream().mapToDouble(Double::doubleValue).toArray();

//        for (int i = 0; i < arr.length; i++) {
//            System.out.println(arr[i]);
//        }

        return arr;
    }

    private boolean isSecondaryStructure(double wavelength) {

        Map<String, Range> map = HardcodedStructures.structures;

        return map.entrySet().stream().anyMatch(
                e -> e.getValue().isInRange(wavelength)
        );
    }

    private double[] findMinimums(Dataset dataset) {

        double[] x = dataset.getX();
        double[] y = dataset.getY();

        List<Double> minValues = new ArrayList<>();

        for (int i = 1; i < x.length-1; i++) {
            if(y[i] < y[i-1] && y[i] < y[i+1]) minValues.add(x[i]);
        }
        return minValues.stream().mapToDouble(Double::doubleValue).toArray();
    }


}
