package com.jakubowskiartur.knowyourprotein.payloads;

import lombok.Data;

@Data
public class SignInRequest {

    private final String usernameOrEmail;
    private final String password;
}
