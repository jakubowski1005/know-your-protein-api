package com.jakubowskiartur.knowyourprotein.pojos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jakubowskiartur.knowyourprotein.computing.pojos.StructureModel;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
public class SpectrumData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NonNull
    private String name;

    @NonNull
    private List<StructureModel> structures;

    private final LocalDate creationDate = LocalDate.now();

    @ManyToOne
    @ToString.Exclude
    @JsonIgnore
    @NonNull
    private User user;
}
