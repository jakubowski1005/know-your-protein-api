package com.jakubowskiartur.knowyourprotein.computing.analyzing;

import com.jakubowskiartur.knowyourprotein.computing.math.Differentiation;
import com.jakubowskiartur.knowyourprotein.computing.pojos.HardcodedStructures;
import com.jakubowskiartur.knowyourprotein.computing.pojos.Dataset;
import com.jakubowskiartur.knowyourprotein.computing.pojos.Range;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PeakFinder {

    public Map<String, Double> getSecondaryStructures(Dataset dataset) {

        Map<String, Double> structures = new HashMap<>();

       double[] peaks = getSecondaryStructurePeaks(dataset);

        double aggregatedStrands = 0;
        int aggregatedStrandsCounter = 0;
        double betaSheet = 0;
        int betaSheetCounter = 0;
        double alphaHelix = 0;
        int alphaHelixCounter = 0;
        double betaTurn = 0;
        int betaTurnCounter = 0;
        double antiparallelBetaSheet = 0;
        int antiparallelBetaSheetCounter = 0;

        for (double peak : peaks) {
            if (peak > 1610 && peak < 1625) {
                aggregatedStrands += peak;
                aggregatedStrandsCounter++;
            }
            if (peak > 1625 && peak < 1648) {
                betaSheet += peak;
                betaSheetCounter++;
            }
            if (peak > 1648 && peak < 1660) {
                alphaHelix += peak;
                alphaHelixCounter++;
            }
            if (peak > 1660 && peak < 1680) {
                betaTurn += peak;
                betaTurnCounter++;
            }
            if (peak > 1675 && peak < 1695) {
                antiparallelBetaSheet += peak;
                antiparallelBetaSheetCounter++;
            }
        }

        if(aggregatedStrandsCounter != 0) structures.put("Aggregated Strands", aggregatedStrands/aggregatedStrandsCounter);
        if(betaSheetCounter != 0) structures.put("β-Sheet", betaSheet/betaSheetCounter);
        if(alphaHelixCounter != 0) structures.put("α-Helix", alphaHelix/alphaHelixCounter);
        if(betaTurnCounter != 0) structures.put("β-Turn", betaTurn/betaTurnCounter);
        if(antiparallelBetaSheetCounter != 0) structures.put("Antiparallel β-Sheet", antiparallelBetaSheet/antiparallelBetaSheetCounter);

        return structures;
    }

    private double[] getSecondaryStructurePeaks(Dataset dataset) {

        Dataset derivative = Differentiation.diff(dataset, 4);

        double[] wavelengths = derivative.getX();
        double[] minimums = findMinimums(derivative);
        double[] secondaryStructures = Arrays.stream(minimums).filter(this::isSecondaryStructure).toArray();

        List<Double> absorptionList = new ArrayList<>();

        for (double secondaryStructure : secondaryStructures) {
            for (double wavelength : wavelengths) {
                if (secondaryStructure == wavelength) {
                    absorptionList.add(secondaryStructure);
                }
            }
        }
        return absorptionList.stream().mapToDouble(Double::doubleValue).toArray();
    }

    private boolean isSecondaryStructure(double wavelength) {

        Map<String, Range> structures = HardcodedStructures.structures;

        return structures.entrySet().stream().anyMatch(
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