package com.jakubowskiartur.knowyourprotein.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jakubowskiartur.knowyourprotein.pojos.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Data
public class UserPrincipal implements UserDetails {

    private static final long serialVersionUID = 2353642365874363728L;

    private final Long id;
    private final String username;
    @JsonIgnore
    private final String email;
    @JsonIgnore
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities = new ArrayList<>();

    public static UserPrincipal create(User user) {
        return new UserPrincipal(user.getId(), user.getUsername(), user.getEmail(), user.getPassword());
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }
}
