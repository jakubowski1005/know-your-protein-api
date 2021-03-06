package com.jakubowskiartur.knowyourprotein.pojos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jakubowskiartur.knowyourprotein.computing.pojos.StructureModel;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class SpectrumData {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @NonNull
    @OneToMany
    private List<StructureModel> structures;

    @ManyToOne
    @ToString.Exclude
    @JsonIgnore
    private User user;
}
