package com.jakubowskiartur.knowyourprotein.services;

import com.jakubowskiartur.knowyourprotein.payloads.ServerResponse;
import com.jakubowskiartur.knowyourprotein.pojos.SpectrumData;
import com.jakubowskiartur.knowyourprotein.pojos.User;
import com.jakubowskiartur.knowyourprotein.repos.SpectrumDataRepository;
import com.jakubowskiartur.knowyourprotein.security.CustomEndpointAuthorization;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class SpectrumDataService {

    private SpectrumDataRepository repository;
    private CustomEndpointAuthorization auth;

    @Inject
    public SpectrumDataService(SpectrumDataRepository repository, CustomEndpointAuthorization auth) {
        this.repository = repository;
        this.auth = auth;
    }

    public ServerResponse<?> retrieveByID(Long id) {

        SpectrumData spectrumData = repository.findById(id).orElseThrow(
                () -> new SecurityException("You have no access to the resources.")
        );

        return ServerResponse.builder()
                .http(HttpStatus.OK)
                .message("Resources loaded successfully.")
                .success(true)
                .body(spectrumData)
                .build();
    }

    public ServerResponse<?> retrieveAllByUser() {

        User loggedInUser = auth.getAuthenticatedUser();
        List<SpectrumData> spectras = repository.getAllByUser(loggedInUser);

        return ServerResponse.builder()
                .success(true)
                .message("Resources loaded successfully.")
                .http(HttpStatus.OK)
                .body(spectras)
                .build();
    }

    public ServerResponse<?> createSpectrum(SpectrumData spectrumData) {

        User loggedInUser = auth.getAuthenticatedUser();
        spectrumData.setUser(loggedInUser);
        repository.save(spectrumData);

        return ServerResponse.builder()
                .http(HttpStatus.CREATED)
                .message("Spectrum data saved successfully.")
                .success(true)
                .body(spectrumData)
                .build();
    }

    public ServerResponse<?> updateSpectrum(Long id, SpectrumData newSpectrum) {

        User loggedInUser = auth.getAuthenticatedUser();
        SpectrumData spectrumData = repository.findById(id).orElse(null);

        if(spectrumData == null) return resourcesNotFound();

        repository.delete(spectrumData);
        newSpectrum.setUser(loggedInUser);
        repository.save(newSpectrum);

        return ServerResponse.builder()
                .http(HttpStatus.CREATED)
                .message("Spectrum data updated successfully.")
                .success(true)
                .body(newSpectrum)
                .build();
    }

    public ServerResponse<?> deleteSpectrumByID(Long id) {

        SpectrumData spectrumData = repository.findById(id).orElse(null);

        if(spectrumData == null) return resourcesNotFound();

        repository.delete(spectrumData);

        return ServerResponse.builder()
                .http(HttpStatus.NO_CONTENT)
                .message("Spectrum data removed successfully.")
                .success(true)
                .build();
    }

    public ServerResponse<?> deleteAll() {

        User loggedInUser = auth.getAuthenticatedUser();

        List<SpectrumData> spectras = repository.getAllByUser(loggedInUser);

        for(SpectrumData data: spectras) {
            repository.delete(data);
        }

        return ServerResponse.builder()
                .http(HttpStatus.NO_CONTENT)
                .message("All spectras removed successfully.")
                .success(true)
                .build();
    }

    private ServerResponse<?> resourcesNotFound() {
        return ServerResponse.builder()
                .http(HttpStatus.NOT_FOUND)
                .success(false)
                .message("Resources with given ID does not exist.")
                .build();
    }
}
