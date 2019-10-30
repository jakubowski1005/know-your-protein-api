package com.jakubowskiartur.knowyourprotein.math;

import java.util.HashMap;
import java.util.Map;

class HardcodedStructures {

    static Map<String, Range> structures = new HashMap<>();

    static {
        structures.put("1", new Range(1615, 1620));
        structures.put("2", new Range(1634, 1638));
        structures.put("3", new Range(1655, 1660));
        structures.put("4", new Range(1680, 1685));
    }
}