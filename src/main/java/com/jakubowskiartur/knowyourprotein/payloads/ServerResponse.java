package com.jakubowskiartur.knowyourprotein.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public abstract class ServerResponse {

    private HttpStatus http;
    private Boolean status;
    private String message;
    private Object body;
}
