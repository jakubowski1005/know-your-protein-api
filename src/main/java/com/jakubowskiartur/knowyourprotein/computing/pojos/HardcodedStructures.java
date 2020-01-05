package com.jakubowskiartur.knowyourprotein.computing.pojos;

import java.util.HashMap;
import java.util.Map;

public class HardcodedStructures {

    public static Map<String, Range> structures = new HashMap<>();

    static {
        structures.put("aggregated-strands", new Range(1610, 1625));
        structures.put("beta-sheet", new Range(1625, 1648));
        structures.put("alpha-helix", new Range(1648, 1660));
        structures.put("beta-turn", new Range(1660, 1680));
        structures.put("antiparallel beta-sheet", new Range(1680, 1690));
    }
}