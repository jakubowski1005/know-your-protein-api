package com.jakubowskiartur.knowyourprotein.services;

import com.jakubowskiartur.knowyourprotein.payloads.ServerResponse;
import com.jakubowskiartur.knowyourprotein.pojos.User;
import com.jakubowskiartur.knowyourprotein.repos.UserRepository;
import com.jakubowskiartur.knowyourprotein.security.UserPrincipal;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class UserService implements UserDetailsService {

    private UserRepository repository;

    @Inject
    public UserService(UserRepository repository) {
        this.repository = repository;
    }


    @Override
    public UserPrincipal loadUserByUsername(String username) throws UsernameNotFoundException {
        return UserPrincipal.create((User) retrieveByUsernameOrEmail(username).getBody());
    }

    public ServerResponse<?> retrieveByID(Long id) {
        User retrievedUser = repository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException(String.format("Cannot find user with ID: %s.", id))
        );

        return ServerResponse.builder()
                .http(HttpStatus.OK)
                .success(true)
                .message("User data loaded successfully.")
                .body(retrievedUser).build();
    }

    public ServerResponse<?> retrieveByUsernameOrEmail(String username) {
        User retrievedUser = repository.findByUsernameOrEmail(username).orElseThrow(
                () -> new UsernameNotFoundException(String.format("Cannot find user with username or e-mail '%s'.", username))
        );

        return ServerResponse.builder()
                .http(HttpStatus.OK)
                .success(true)
                .message("User data loaded successfully.")
                .body(retrievedUser).build();
    }

    public ServerResponse<?> deleteUser(Long id) {
        User retrievedUser = repository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException(String.format("Cannot find user with ID: %s.", id))
        );

        repository.delete(retrievedUser);
        return ServerResponse.builder()
                .success(true)
                .message("User removed successfully.")
                .http(HttpStatus.OK)
                .build();
    }
}