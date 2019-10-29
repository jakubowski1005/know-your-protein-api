package com.jakubowskiartur.knowyourprotein.daos;

import com.jakubowskiartur.knowyourprotein.pojos.SpectrumData;
import com.jakubowskiartur.knowyourprotein.pojos.User;
import com.jakubowskiartur.knowyourprotein.repos.SpectrumDataRepository;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

@Component
public class SpectrumDataDAO {

    private SpectrumDataRepository spectrumDataRepository;

    @Inject
    public SpectrumDataDAO(SpectrumDataRepository spectrumDataRepository) {
        this.spectrumDataRepository = spectrumDataRepository;
    }

    public SpectrumData retrieveByID(UUID id) {
        return null;
    }

    public List<SpectrumData> retrieveAllByUser(User user) {
        return null;
    }

    public void createSpectrumData(SpectrumData spectrumData) {
    }

    public void updateSpectrumDataByID(UUID id, SpectrumData newSpectrum) {
    }

    public void deleteByID(UUID id) {
    }

    public void deleteAll() {
    }
}
