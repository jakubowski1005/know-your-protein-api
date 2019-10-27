package com.jakubowskiartur.knowyourprotein.pojos;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"username"}),
        @UniqueConstraint(columnNames = {"email"})
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NonNull
    private String username;

    @NonNull
    @Email
    private String email;

    @NonNull
    @Size(min = 6)
    private String password;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="user")
    private List<SpectrumData> spectras = new ArrayList<>();
}
