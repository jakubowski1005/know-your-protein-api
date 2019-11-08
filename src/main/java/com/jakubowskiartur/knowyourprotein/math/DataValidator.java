package com.jakubowskiartur.knowyourprotein.math;

import com.jakubowskiartur.knowyourprotein.payloads.ServerResponse;
import org.springframework.http.HttpStatus;

class DataValidator {

    ServerResponse<?> buildErrorResponse() {

        return ServerResponse.builder()
                .http(HttpStatus.BAD_REQUEST)
                .success(false)
                .message("Wrong dataset. Please check if file is valid protein FTIR spectrum.")
                .body(null)
                .build();
    }

    boolean isValid(Dataset dataset) {

        double[] x = dataset.getX();

        for (int i = 1; i < x.length; i++) {
            if(x[i-1] > x[i] || x[i] < 0) return false;
        }
        return true;
    }
}