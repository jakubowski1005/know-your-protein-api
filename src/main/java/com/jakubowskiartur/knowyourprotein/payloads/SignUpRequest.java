package com.jakubowskiartur.knowyourprotein.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {

    private  String username;
    private  String email;
    private  String password;
}
