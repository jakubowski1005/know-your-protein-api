package com.jakubowskiartur.knowyourprotein.computing.pojos;

import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
public class StructureModel {

    @Id
    @GeneratedValue
    private UUID id;

    @NonNull private String name;

    @NonNull
    @OneToOne
    private Dataset data;

    @NonNull private Double absorbance;
}