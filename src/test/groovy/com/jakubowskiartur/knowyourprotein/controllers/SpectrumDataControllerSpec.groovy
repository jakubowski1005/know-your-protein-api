package com.jakubowskiartur.knowyourprotein.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.jakubowskiartur.knowyourprotein.computing.pojos.StructureModel
import com.jakubowskiartur.knowyourprotein.payloads.ServerResponse
import com.jakubowskiartur.knowyourprotein.pojos.SpectrumData
import com.jakubowskiartur.knowyourprotein.services.SpectrumDataService
import org.springframework.http.HttpStatus
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

class SpectrumDataControllerSpec extends Specification {

    SpectrumDataController controller
    SpectrumDataService service
    MockMvc mockMvc
    ObjectMapper mapper = new ObjectMapper()

    SpectrumData data1
    SpectrumData data2
    String json1
    String json2

    void setup() {
        service = Mock(SpectrumDataService)
        controller = new SpectrumDataController(service)
        mockMvc = MockMvcBuilders.standaloneSetup(controller).alwaysDo(MockMvcResultHandlers.print()).build()

        data1 = new SpectrumData(1, 'name1', new ArrayList<StructureModel>(), null)
        data2 = new SpectrumData(2, 'name2', new ArrayList<StructureModel>(), null)
        json1 = mapper.writeValueAsString(data1)
        json2 = mapper.writeValueAsString(data2)
    }

    void 'should return not found response'() {
        given:
        ServerResponse response = ServerResponse.builder().http(HttpStatus.NOT_FOUND).success(false).message("Resources with given ID does not exist.").build()
        service.retrieveByID(3) >> response
        service.deleteSpectrumByID(3) >> response
        String responseJson = mapper.writeValueAsString(response)

        when:
        String response1 = mockMvc.perform(MockMvcRequestBuilders.get("/data/spectras/3")).andReturn().getResponse().getContentAsString()
        String response2 = mockMvc.perform(MockMvcRequestBuilders.delete("/data/spectras/3")).andReturn().getResponse().getContentAsString()

        then:
        response1 == responseJson
        response2 == responseJson
    }

    void 'should retrieve data by id'() {
        given:
        ServerResponse response = ServerResponse.builder().http(HttpStatus.OK).message("Resources loaded successfully.").success(true).body(data1).build()
        service.retrieveByID(1) >> response
        String responseJson = mapper.writeValueAsString(response)

        expect:
        mockMvc.perform(MockMvcRequestBuilders.get("/data/spectras/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(responseJson))
    }

    void 'should retrieve all data'() {
        given:
        List<SpectrumData> list = new ArrayList<>()
        list << data1
        list << data2
        ServerResponse response = ServerResponse.builder().success(true).message("Resources loaded successfully.").http(HttpStatus.OK).body(list).build()
        service.retrieveAllByUser() >> response
        String responseJson = mapper.writeValueAsString(response)

        expect:
        mockMvc.perform(MockMvcRequestBuilders.get("/data/spectras"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(responseJson))
    }

    // 'should create data'
    void 'should create data'() {
        given:
        ServerResponse response = ServerResponse.builder().http(HttpStatus.CREATED)
                .message("Spectrum data saved successfully.")
                .success(true)
                .body(data1).build()
        service.createSpectrum(data1) >> response
        String responseJson = mapper.writeValueAsString(response)

        expect:
        mockMvc.perform(MockMvcRequestBuilders.post("/data/spectras"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(responseJson))
    }

    void 'should update data'() {
        given:
        ServerResponse response = ServerResponse.builder().http(HttpStatus.CREATED)
                .message("Spectrum data updated successfully.")
                .success(true)
                .body(data2).build()
        service.updateSpectrum(1, data2) >> response
        String responseJson = mapper.writeValueAsString(response)

        expect:
        mockMvc.perform(MockMvcRequestBuilders.put("/data/spectras"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(responseJson))
    }

    void 'should delete data by id'() {
        given:
        ServerResponse response = ServerResponse.builder().http(HttpStatus.NO_CONTENT)
                .message("Spectrum data removed successfully.")
                .success(true)
                .build()
        service.deleteSpectrumByID(1) >> response
        String responseJson = mapper.writeValueAsString(response)

        expect:
        mockMvc.perform(MockMvcRequestBuilders.delete("/data/spectras/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(responseJson))
    }

    void 'should delete all data'() {
        given:
        ServerResponse response = ServerResponse.builder().http(HttpStatus.NO_CONTENT)
                .message("All spectras removed successfully.")
                .success(true)
                .build()
        service.deleteAll() >> response
        String responseJson = mapper.writeValueAsString(response)

        expect:
        mockMvc.perform(MockMvcRequestBuilders.delete("/data/spectras"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(responseJson))
    }
}
