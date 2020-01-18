package com.jakubowskiartur.knowyourprotein.services;

import com.jakubowskiartur.knowyourprotein.payloads.ServerResponse;
import com.jakubowskiartur.knowyourprotein.payloads.SignInRequest;
import com.jakubowskiartur.knowyourprotein.payloads.SignUpRequest;
import com.jakubowskiartur.knowyourprotein.pojos.User;
import com.jakubowskiartur.knowyourprotein.repos.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthService {

    private UserRepository repository;
    private AuthenticationManager manager;
    private PasswordEncoder encoder;
//    private TokenStore store;

    //constructor injection

    public ServerResponse<?> authenticateUser(SignInRequest request) {

        Authentication authentication = manager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsernameOrEmail(),
                request.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        //String token = store.generateTokenFromAuthentication(authentication);

//        if(!store.validateToken(token)) {
//            return ServerResponse.builder()
//                    .http(HttpStatus.UNAUTHORIZED)
//                    .message("User does not exist.")
//                    .success(false)
//                    .build();
//        }

//        return ServerResponse.builder()
//                .http(HttpStatus.OK)
//                .success(true)
//                .message("User has been logged.")
//                .body(Object with tokenType and token)
//                .build();
        return null;
    }

    public ServerResponse<?> registerUser(SignUpRequest request) {


        if(repository.existsByEmail(request.getEmail())) {
            return ServerResponse.builder()
                    .http(HttpStatus.BAD_REQUEST)
                    .message("E-mail already in use!")
                    .success(false)
                    .build();
        }

        if(repository.existsByUsername(request.getUsername())) {
            return ServerResponse.builder()
                    .http(HttpStatus.BAD_REQUEST)
                    .message("Username already taken!")
                    .success(false)
                    .build();
        }

        String encodedPassword = encoder.encode(request.getPassword());
        User user = new User(request.getUsername(), request.getEmail(), encodedPassword);
        repository.save(user);

        log.info("Created new user with username: '{}'", user.getUsername());
        return ServerResponse.builder()
                .http(HttpStatus.CREATED)
                .message("User registered successfully!")
                .success(true)
                .build();
    }
}
