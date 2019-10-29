package com.jakubowskiartur.knowyourprotein.pojos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jakubowskiartur.knowyourprotein.math.Dataset;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Entity
@Data
//@Builder
@NoArgsConstructor
public class SpectrumData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NonNull
    private String name;

    @NonNull
    private Dataset originalSpectrum;

    @NonNull
    private Dataset amide;

    @NonNull
    private Dataset derivative;

    @NonNull
    private List<Map<String, Double>> structures;

    private final LocalDate creationDate = LocalDate.now();

    @ManyToOne
    @ToString.Exclude
    @JsonIgnore
    @NonNull
    private User user;
}
