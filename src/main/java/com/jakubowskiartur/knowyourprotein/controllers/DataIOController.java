package com.jakubowskiartur.knowyourprotein.controllers;

import com.jakubowskiartur.knowyourprotein.computing.SpectrumAnalyzer;
import com.jakubowskiartur.knowyourprotein.computing.pojos.Dataset;
import com.jakubowskiartur.knowyourprotein.payloads.ServerResponse;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

@RestController
@CrossOrigin(origins = "*")
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
}
