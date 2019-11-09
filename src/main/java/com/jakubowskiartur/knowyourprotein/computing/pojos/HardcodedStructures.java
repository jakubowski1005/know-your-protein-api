package com.jakubowskiartur.knowyourprotein.computing.pojos;

import java.util.HashMap;
import java.util.Map;

public class HardcodedStructures {

    public static Map<String, Range> structures = new HashMap<>();

    static {
        structures.put("aggregated-strands", new Range(1610, 1628));
        structures.put("beta-sheet", new Range(1629, 1640));
        structures.put("unordered", new Range(1641, 1648));
        structures.put("alpha-helix", new Range(1649, 1660));
        structures.put("310-helix", new Range(1661, 1670));
        structures.put("antiparallel beta-sheet", new Range(1675, 1695));
    }
}