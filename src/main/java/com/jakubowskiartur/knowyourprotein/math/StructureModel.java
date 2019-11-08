package com.jakubowskiartur.knowyourprotein.math;

import lombok.Data;
import lombok.NonNull;

@Data
public class StructureModel {

    @NonNull private String name;
    @NonNull private Dataset data;
    @NonNull private double absorbance;
}