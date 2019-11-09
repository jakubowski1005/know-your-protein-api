package com.jakubowskiartur.knowyourprotein.computing;

import com.jakubowskiartur.knowyourprotein.computing.analyzing.DataValidator;
import com.jakubowskiartur.knowyourprotein.computing.analyzing.Deconvolution;
import com.jakubowskiartur.knowyourprotein.computing.analyzing.ResponseCreator;
import com.jakubowskiartur.knowyourprotein.computing.math.BandSlicer;
import com.jakubowskiartur.knowyourprotein.computing.pojos.Dataset;
import com.jakubowskiartur.knowyourprotein.computing.quality.QualityEnhancer;
import com.jakubowskiartur.knowyourprotein.computing.pojos.StructureModel;
import com.jakubowskiartur.knowyourprotein.payloads.ServerResponse;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
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

    public ServerResponse<?> analyzeSpectrum(Dataset dataset) {

        if(!validator.isValid(dataset)) {
            return validator.buildErrorResponse();
        }

        Dataset amide;
        List<StructureModel> structures;

        amide = BandSlicer.slice(dataset, 1600, 1700);
        amide = enhancer.enhanceQuality(amide);
        structures = deconvolution.deconvolve(amide);

        return creator.buildSpectrumData(structures);
    }
}
