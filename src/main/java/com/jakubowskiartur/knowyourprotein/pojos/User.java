package com.jakubowskiartur.knowyourprotein.pojos;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Document
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class User implements UserDetails {

    @Id
    private String id;

    @NonNull
    private String username;

    @NonNull
    @Email(message = "E-mail should be valid.")
    private String email;

    @NonNull
    @Size(min = 6, message = "Password should not be less than 6 characters.")
    private String password;

    private List<SpectrumData> spectras = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
