package com.jakubowskiartur.knowyourprotein.pojos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jakubowskiartur.knowyourprotein.computing.pojos.StructureModel;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Document
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class SpectrumData {

    @Id
    private String id;

    @NonNull
    private String name;

    @NonNull
    private List<StructureModel> structures;

    private final LocalDate creationDate = LocalDate.now();

    @ToString.Exclude
    @JsonIgnore
    private User user;
}
