package com.jakubowskiartur.knowyourprotein.controllers;

import com.jakubowskiartur.knowyourprotein.payloads.ServerResponse;
import com.jakubowskiartur.knowyourprotein.payloads.SignInRequest;
import com.jakubowskiartur.knowyourprotein.payloads.SignUpRequest;
import com.jakubowskiartur.knowyourprotein.services.AuthService;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthService authService;

    @Inject
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signin")
    public ServerResponse<?> authenticateUser(@Valid @RequestBody SignInRequest request) {
        return authService.authenticateUser(request);
    }

    @PostMapping("/signup")
    public ServerResponse<?> registerUser(@Valid @RequestBody SignUpRequest request) {
        return authService.registerUser(request);
    }
}