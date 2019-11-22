package com.jakubowskiartur.knowyourprotein.controllers;

import com.jakubowskiartur.knowyourprotein.computing.SpectrumAnalyzer;
import com.jakubowskiartur.knowyourprotein.computing.pojos.Dataset;
import com.jakubowskiartur.knowyourprotein.payloads.ServerResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

@RestController
@CrossOrigin(origins = "${client.url}")
public class DataIOController {

    private SpectrumAnalyzer spectrumAnalyzer;

    @Inject
    public DataIOController(SpectrumAnalyzer spectrumAnalyzer) {
        this.spectrumAnalyzer = spectrumAnalyzer;
    }

    @PostMapping("/analyze")
    public ServerResponse<?> analyzeSpectrum(@RequestBody Dataset dataset) {
        return spectrumAnalyzer.analyzeSpectrum(dataset);
    }

    @GetMapping("/test")
    public ServerResponse<?> getTestData() {

        double[] x = new double[]{0.1, 0.2, 0.3, 0.4 , 0.5, 0.6, 0.7};
        double[] y = new double[]{0.2, 0.22, 0.3, 0.33, 0.4, 0.44, 0.5};

        Dataset test = Dataset.merge(x, y);

        return ServerResponse.builder().http(HttpStatus.OK).message("Hello world").success(true).body(test).build();
    }
}
