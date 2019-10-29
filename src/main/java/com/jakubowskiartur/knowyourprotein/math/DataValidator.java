package com.jakubowskiartur.knowyourprotein.math;

import com.jakubowskiartur.knowyourprotein.payloads.ServerResponse;
import org.springframework.http.HttpStatus;

public class DataValidator {

    public ServerResponse<?> buildErrorResponse() {

        return ServerResponse.builder()
                .http(HttpStatus.BAD_REQUEST)
                .success(false)
                .message("Wrong dataset. Please check if file is valid protein FTIR spectrum.")
                .body(null)
                .build();
    }

    public boolean isValid(Dataset dataset) {
        return true;
    }
}