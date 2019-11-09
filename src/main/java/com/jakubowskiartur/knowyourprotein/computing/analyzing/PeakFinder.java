package com.jakubowskiartur.knowyourprotein.computing.analyzing;

import com.jakubowskiartur.knowyourprotein.computing.math.Differentiation;
import com.jakubowskiartur.knowyourprotein.computing.pojos.HardcodedStructures;
import com.jakubowskiartur.knowyourprotein.computing.pojos.Dataset;
import com.jakubowskiartur.knowyourprotein.computing.pojos.Range;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
class PeakFinder {

    Map<String, Double> getSecondaryStructures(Dataset dataset) {

        Map<String, Double> structures = new HashMap<>();

        double[] peaks = getSecondaryStructurePeaks(dataset);

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

        for (double peak : peaks) {
            if (peak > 1610 && peak < 1628) {
                aggregatedStrands += peak;
                aggregatedStrandsCounter++;
            }
            if (peak > 1629 && peak < 1640) {
                betaSheet += peak;
                betaSheetCounter++;
            }
            if (peak > 1641 && peak < 1648) {
                unordered += peak;
                unorderedCounter++;
            }
            if (peak > 1649 && peak < 1660) {
                alphaHelix += peak;
                alphaHelixCounter++;
            }
            if (peak > 1661 && peak < 1670) {
                helix310 += peak;
                helix310Counter++;
            }
            if (peak > 1675 && peak < 1695) {
                antiparallelBetaSheet += peak;
                antiparallelBetaSheetCounter++;
            }
        }

        structures.put("Aggregated Strands", aggregatedStrands/aggregatedStrandsCounter);
        structures.put("β-Sheet", betaSheet/betaSheetCounter);
        structures.put("Unordered Structures", unordered/unorderedCounter);
        structures.put("α-Helix", alphaHelix/alphaHelixCounter);
        structures.put("310-Helix", helix310/helix310Counter);
        structures.put("Antiparallel β-Sheet", antiparallelBetaSheet/antiparallelBetaSheetCounter);

        return structures;
    }

    private double[] getSecondaryStructurePeaks(Dataset dataset) {

        Dataset derivative = Differentiation.diff(dataset, 2);

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