package com.jakubowskiartur.knowyourprotein.payloads;

import lombok.Data;

@Data
public class SignUpRequest {

    private final String username;
    private final String email;
    private final String password;
}
