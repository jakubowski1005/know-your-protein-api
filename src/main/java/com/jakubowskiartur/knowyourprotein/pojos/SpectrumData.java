package com.jakubowskiartur.knowyourprotein.pojos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jakubowskiartur.knowyourprotein.math.Dataset;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
public class SpectrumData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NonNull
    private String name;

    //private Dataset orginalSpectrum;

    //private Dataset derivative;

    //private Dataset amide;

    //private List<SpectrumData> spectras = new ArrayList<>();

    // private Structures data

    private LocalDate creationDate = LocalDate.now();

    @ManyToOne
    @ToString.Exclude
    @JsonIgnore
    private User user;
}
