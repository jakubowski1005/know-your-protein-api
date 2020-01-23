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

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String username;

    @NonNull
    @Email(message = "E-mail should be valid.")
    private String email;

    @NonNull
    @Size(min = 6, message = "Password should not be less than 6 characters.")
    private String password;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="user")
    private List<SpectrumData> spectras = new ArrayList<>();
}
