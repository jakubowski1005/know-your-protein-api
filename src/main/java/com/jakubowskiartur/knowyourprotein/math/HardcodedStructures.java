package com.jakubowskiartur.knowyourprotein.math;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HardcodedStructures {

    static Map<String, Range> structures = new HashMap<>();

    static {
        structures.put("string1", new Range(1620, 1625));
        structures.put("strin2", new Range(1650, 1655));
    }
}
