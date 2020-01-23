package com.jakubowskiartur.knowyourprotein.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignInRequest {

     private String usernameOrEmail;
     private String password;
}
