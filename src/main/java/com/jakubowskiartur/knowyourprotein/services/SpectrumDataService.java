package com.jakubowskiartur.knowyourprotein.services;

import com.jakubowskiartur.knowyourprotein.daos.SpectrumDataDAO;
import com.jakubowskiartur.knowyourprotein.payloads.ServerResponse;
import com.jakubowskiartur.knowyourprotein.pojos.SpectrumData;
import com.jakubowskiartur.knowyourprotein.pojos.User;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.UUID;

@Service
public class SpectrumDataService {

    private SpectrumDataDAO spectrumDataDAO;

    @Inject
    public SpectrumDataService(SpectrumDataDAO spectrumDataDAO) {
        this.spectrumDataDAO = spectrumDataDAO;
    }

    public ServerResponse<?> retrieveByID(UUID id) {
        return null;
    }

    public ServerResponse<?> retrieveAllByUser() {
        return null;
    }

    public ServerResponse<?> createSpectrum(SpectrumData spectrumData) {
        return null;
    }

    public ServerResponse<?> updateSpectrum(UUID id, SpectrumData newSpectrum) {
        return null;
    }

    public ServerResponse<?> deleteSpectrumByID(UUID id) {
        return null;
    }

    public ServerResponse<?> deleteAll() {
        return null;
    }
}
