package com.jakubowskiartur.knowyourprotein.math;

import com.jakubowskiartur.knowyourprotein.payloads.ServerResponse;
import org.springframework.stereotype.Service;

@Service
public class SpectrumAnalyzer {

    private DataValidator validator;
    private PeakFinder peakFinder;
    // private deconvolution class
    private ResponseCreator creator;

    public ServerResponse<?> analyzeSpectrum(Dataset dataset) {

        if(!validator.isValid(dataset)) {
            return validator.buildErrorResponse();
        }

        // slice and quality improve

        // numerical process

        // find peaks

        // deconvolution

        // create response

        return null;
    }
}
