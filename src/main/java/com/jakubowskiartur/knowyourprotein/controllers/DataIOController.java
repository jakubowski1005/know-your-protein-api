package com.jakubowskiartur.knowyourprotein.controllers;

import com.jakubowskiartur.knowyourprotein.math.Dataset;
import com.jakubowskiartur.knowyourprotein.math.SpectrumAnalyzer;
import com.jakubowskiartur.knowyourprotein.payloads.ServerResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.inject.Inject;

@Service
@RequestMapping("/analyze")
@CrossOrigin(origins = "${client.url}")
public class DataIOController {

    private SpectrumAnalyzer spectrumAnalyzer;

    @Inject
    public DataIOController(SpectrumAnalyzer spectrumAnalyzer) {
        this.spectrumAnalyzer = spectrumAnalyzer;
    }

    @PostMapping
    public ServerResponse<?> analyzeSpectrum(Dataset dataset) {
        return spectrumAnalyzer.analyzeSpectrum(dataset);
    }
}
