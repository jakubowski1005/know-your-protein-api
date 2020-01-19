package com.jakubowskiartur.knowyourprotein.payloads;

import lombok.Data;
import lombok.NonNull;

@Data
public class CustomTokenResponse {

    @NonNull private String tokenType;
    @NonNull private String tokenValue;
}
