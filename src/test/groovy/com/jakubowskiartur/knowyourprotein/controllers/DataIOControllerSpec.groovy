package com.jakubowskiartur.knowyourprotein.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.jakubowskiartur.knowyourprotein.computing.SpectrumAnalyzer
import com.jakubowskiartur.knowyourprotein.computing.pojos.Dataset
import com.jakubowskiartur.knowyourprotein.payloads.ServerResponse
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

class DataIOControllerSpec extends Specification {

    DataIOController controller
    SpectrumAnalyzer analyzer
    MockMvc mockMvc
    ObjectMapper mapper = new ObjectMapper()

    Dataset dataset
    String datasetJSON

    void setup() {
        analyzer = Mock(SpectrumAnalyzer)
        controller = new DataIOController(analyzer)
        mockMvc = MockMvcBuilders.standaloneSetup(controller).alwaysDo(MockMvcResultHandlers.print()).build()

        double[] x = [1, 2, 3, 4, 5, 6, 7, 8] as double[]
        double[] y = [1, 3, 2, 1, 4, 1, 2, 3] as double[]
        dataset = new Dataset(x, y)
        datasetJSON = mapper.writeValueAsString(dataset)
    }

    void 'should analyze data'() {
        given:
        analyzer.analyzeSpectrum(dataset) >> ServerResponse.builder().http(HttpStatus.OK).success(true).build()
        String response = mapper.writeValueAsString(analyzer.analyzeSpectrum(dataset))

        expect:
        mockMvc.perform(MockMvcRequestBuilders.post('/analyze')
                .contentType(MediaType.APPLICATION_JSON).content(datasetJSON))
                .andReturn().getResponse().getContentAsString() == response
    }
}
