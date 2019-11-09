package com.jakubowskiartur.knowyourprotein.math;

import com.jakubowskiartur.knowyourprotein.payloads.ServerResponse;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

//@Service
public class SpectrumAnalyzer {

    private DataValidator validator;
    private Deconvolution deconvolution;
    private ResponseCreator creator;
    private QualityEnhancer enhancer;

    @Inject
    public SpectrumAnalyzer(DataValidator validator, Deconvolution deconvolution, ResponseCreator creator, QualityEnhancer enhancer) {
        this.validator = validator;
        this.deconvolution = deconvolution;
        this.creator = creator;
        this.enhancer = enhancer;
    }

    public List<StructureModel> analyzeSpectrum(Dataset dataset) {

//        if(!validator.isValid(dataset)) {
//            return validator.buildErrorResponse();
//        }

        Dataset amide;
        List<StructureModel> structures;

        amide = BandSlicer.slice(dataset, 1600, 1700);
        amide = enhancer.enhanceQuality(amide);

        structures = deconvolution.deconvolve(amide);


        // create response

        return structures;
    }
}
