package com.jakubowskiartur.knowyourprotein.controllers;

import com.jakubowskiartur.knowyourprotein.payloads.ServerResponse;
import com.jakubowskiartur.knowyourprotein.pojos.SpectrumData;
import com.jakubowskiartur.knowyourprotein.services.SpectrumDataService;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.UUID;

@RestController
@RequestMapping("/data")
@CrossOrigin(origins = "${client.url}")
public class SpectrumDataController {

    private SpectrumDataService spectrumDataService;

    @Inject
    public SpectrumDataController(SpectrumDataService spectrumDataService) {
        this.spectrumDataService = spectrumDataService;
    }

    @GetMapping("/spectras/{id}")
    public ServerResponse<?> retrieveSpectrumByID(@PathVariable UUID id) {
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
    public ServerResponse<?> updateSpectrum(@PathVariable UUID id, SpectrumData newSpectrum) {
        return spectrumDataService.updateSpectrum(id, newSpectrum);
    }

    @DeleteMapping("/spectras/{id}")
    public ServerResponse<?> deleteSpectrum(@PathVariable UUID id) {
        return spectrumDataService.deleteSpectrumByID(id);
    }

    @DeleteMapping("/spectras")
    public ServerResponse<?> deleteAll() {
        return spectrumDataService.deleteAll();
    }
}