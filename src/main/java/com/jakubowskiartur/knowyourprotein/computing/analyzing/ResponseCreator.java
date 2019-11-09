package com.jakubowskiartur.knowyourprotein.computing.analyzing;

import com.jakubowskiartur.knowyourprotein.computing.pojos.StructureModel;
import com.jakubowskiartur.knowyourprotein.payloads.ServerResponse;
import com.jakubowskiartur.knowyourprotein.pojos.SpectrumData;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResponseCreator {

    public ServerResponse<?> buildSpectrumData(List<StructureModel> structures) {
        return ServerResponse.builder()
                .http(HttpStatus.OK)
                .success(true)
                .message("Spectrum data generated successfully.")
                .body(SpectrumData.builder().structures(structures).build())
                .build();
    }
}
