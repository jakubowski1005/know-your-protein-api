package com.jakubowskiartur.knowyourprotein.controllers;

import com.jakubowskiartur.knowyourprotein.payloads.ServerResponse;
import com.jakubowskiartur.knowyourprotein.pojos.SpectrumData;
import com.jakubowskiartur.knowyourprotein.services.SpectrumDataService;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

@RestController
@RequestMapping("/data")
@CrossOrigin(origins = "*")
public class SpectrumDataController {

    private SpectrumDataService spectrumDataService;

    @Inject
    public SpectrumDataController(SpectrumDataService spectrumDataService) {
        this.spectrumDataService = spectrumDataService;
    }

    @GetMapping("/spectras/{id}")
    public ServerResponse<?> retrieveSpectrumByID(@PathVariable Long id) {
        return spectrumDataService.retrieveByID(id);
    }

    @GetMapping("/spectras")
    public ServerResponse<?> retrieveAllSpectrasByUser() {
        return spectrumDataService.retrieveAllByUser();
    }

    @PostMapping("/spectras")
    public ServerResponse<?> createSpectrum(SpectrumData spectrumData) {
        return spectrumDataService.createSpectrum(spectrumData);
    }

    @PutMapping("/spectras/{id}")
    public ServerResponse<?> updateSpectrum(@PathVariable Long id, SpectrumData newSpectrum) {
        return spectrumDataService.updateSpectrum(id, newSpectrum);
    }

    @DeleteMapping("/spectras/{id}")
    public ServerResponse<?> deleteSpectrum(@PathVariable Long id) {
        return spectrumDataService.deleteSpectrumByID(id);
    }

    @DeleteMapping("/spectras")
    public ServerResponse<?> deleteAll() {
        return spectrumDataService.deleteAll();
    }
}