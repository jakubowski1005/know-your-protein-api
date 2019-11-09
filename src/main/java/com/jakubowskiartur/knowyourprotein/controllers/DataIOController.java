package com.jakubowskiartur.knowyourprotein.controllers;

import com.jakubowskiartur.knowyourprotein.math.Dataset;
import com.jakubowskiartur.knowyourprotein.math.SpectrumAnalyzer;
import com.jakubowskiartur.knowyourprotein.payloads.ServerResponse;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

@RestController
@RequestMapping("/analyze")
@CrossOrigin(origins = "${client.url}")
public class DataIOController {

    private SpectrumAnalyzer spectrumAnalyzer;

    @Inject
    public DataIOController(SpectrumAnalyzer spectrumAnalyzer) {
        this.spectrumAnalyzer = spectrumAnalyzer;
    }

//    @PostMapping
//    public ServerResponse<?> analyzeSpectrum(@RequestBody Dataset dataset) {
//        return spectrumAnalyzer.analyzeSpectrum(dataset);
//    }
}
