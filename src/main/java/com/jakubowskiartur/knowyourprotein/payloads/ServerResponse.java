package com.jakubowskiartur.knowyourprotein.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class ServerResponse<T> {

    private HttpStatus http;
    private Boolean status;
    private String message;
    private T body;
}
