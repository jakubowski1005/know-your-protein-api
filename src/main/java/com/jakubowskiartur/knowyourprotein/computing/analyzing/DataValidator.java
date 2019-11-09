package com.jakubowskiartur.knowyourprotein.computing.analyzing;

import com.jakubowskiartur.knowyourprotein.computing.pojos.Dataset;
import com.jakubowskiartur.knowyourprotein.payloads.ServerResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
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

        double[] x = dataset.getX();

        for (int i = 1; i < x.length; i++) {
            if(x[i-1] > x[i] || x[i] < 0) return false;
        }
        return true;
    }
}