package com.jakubowskiartur.knowyourprotein.services;

import com.jakubowskiartur.knowyourprotein.daos.UserDAO;
import com.jakubowskiartur.knowyourprotein.payloads.ServerResponse;
import com.jakubowskiartur.knowyourprotein.payloads.SignInRequest;
import com.jakubowskiartur.knowyourprotein.payloads.SignUpRequest;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    // inject UserDAO

    private UserDAO userDAO;
//    private AuthenticationManager manager;
//    private PasswordEncoder encoder;
    // private TokenStore

    public ServerResponse<?> authenticateUser(SignInRequest request) {
        return null;
    }

    public ServerResponse<?> registerUser(SignUpRequest request) {
        return null;
    }
}
