package com.jakubowskiartur.knowyourprotein.computing.pojos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Range {

    private double minValue;
    private double maxValue;

    public boolean isInRange(double value) {
        return value >= minValue && value <= maxValue;
    }
}