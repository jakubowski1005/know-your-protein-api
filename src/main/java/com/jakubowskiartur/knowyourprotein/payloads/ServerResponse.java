package com.jakubowskiartur.knowyourprotein.payloads;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ServerResponse<T> {

    private HttpStatus http;
    private Boolean success;
    private String message;
    private T body;
}
