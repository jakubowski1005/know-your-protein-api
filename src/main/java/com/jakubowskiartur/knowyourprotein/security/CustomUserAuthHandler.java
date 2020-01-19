package com.jakubowskiartur.knowyourprotein.security;

import com.jakubowskiartur.knowyourprotein.pojos.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CustomUserAuthHandler {

    public User getAuthenticationUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ((User) authentication.getPrincipal());
    }
}
