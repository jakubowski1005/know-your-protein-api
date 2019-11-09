package com.jakubowskiartur.knowyourprotein.computing.quality;

import com.jakubowskiartur.knowyourprotein.computing.pojos.Dataset;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class QualityEnhancer {

    private SGFilter filter;
    private BaselineCorrector corrector;

    @Inject
    public QualityEnhancer(SGFilter filter, BaselineCorrector corrector) {
        this.filter = filter;
        this.corrector = corrector;
    }

    public Dataset enhanceQuality(Dataset dataset) {
        Dataset filtered = filter.smooth(dataset);
        return corrector.subtract(filtered);
    }
}