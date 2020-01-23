package com.jakubowskiartur.knowyourprotein.security;

import com.jakubowskiartur.knowyourprotein.pojos.User;
import com.jakubowskiartur.knowyourprotein.repos.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class CustomEndpointAuthorization {

    private UserRepository userRepository;

    @Inject
    public CustomEndpointAuthorization(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getAuthenticatedUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public boolean isUserAuthorized(Authentication authentication, Long userId) {

        User user = userRepository.findById(userId).orElseThrow(
                () -> new UsernameNotFoundException("User with ID: " + userId + " not found.")
        );

        String current = authentication.getName();
        return current.equals(user.getUsername());
    }

    public boolean isUserAuthorizedToGetUserByUsername(Authentication authentication, String usernameOrEmail) {

        User user = userRepository.findByUsernameOrEmail(usernameOrEmail).orElseThrow(
                () -> new UsernameNotFoundException("User with username: " + usernameOrEmail + " not found.")
        );

        String current = authentication.getName();
        return current.equals(user.getUsername());
    }
}
