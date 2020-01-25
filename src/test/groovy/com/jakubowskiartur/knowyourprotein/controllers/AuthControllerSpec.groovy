package com.jakubowskiartur.knowyourprotein.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.jakubowskiartur.knowyourprotein.payloads.ServerResponse
import com.jakubowskiartur.knowyourprotein.payloads.SignInRequest
import com.jakubowskiartur.knowyourprotein.payloads.SignUpRequest
import com.jakubowskiartur.knowyourprotein.services.AuthService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

class AuthControllerSpec extends Specification {

    AuthController controller
    AuthService service
    MockMvc mockMvc
    ObjectMapper mapper = new ObjectMapper()

    SignInRequest signInRequest
    SignUpRequest signUpRequest

    String signInRequestJSON
    String signUpRequestJSON

    void setup() {
        service = Mock(AuthService)
        controller = new AuthController(service)
        mockMvc = MockMvcBuilders.standaloneSetup(controller).alwaysDo(MockMvcResultHandlers.print()).build()

        signInRequest = new SignInRequest([usernameOrEmail: 'username', password: 'password'])
        signUpRequest = new SignUpRequest([username: 'username', email: 'email', password: 'password'])

        signInRequestJSON = mapper.writeValueAsString(signInRequest)
        signUpRequestJSON = mapper.writeValueAsString(signUpRequest)
    }

    void 'should login successfully'() {
        given:
        service.authenticateUser(signInRequest) >> ServerResponse.builder().http(HttpStatus.OK).success(true).build()
        String response = mapper.writeValueAsString(service.authenticateUser(signInRequest))

        expect:
        mockMvc.perform(MockMvcRequestBuilders.post('/auth/signin')
                .contentType(MediaType.APPLICATION_JSON).content(signInRequestJSON))
                .andReturn().getResponse().getContentAsString() == response
    }

    void 'should not login'() {
        given:
        service.authenticateUser(signInRequest) >> ServerResponse.builder().http(HttpStatus.UNAUTHORIZED).success(false).build()
        String response = mapper.writeValueAsString(service.authenticateUser(signInRequest))

        expect:
        mockMvc.perform(MockMvcRequestBuilders.post('/auth/signin')
                .contentType(MediaType.APPLICATION_JSON).content(signInRequestJSON))
                .andReturn().getResponse().getContentAsString() == response
    }

    void 'should register successfully'() {
        given:
        service.registerUser(signUpRequest) >> ServerResponse.builder().http(HttpStatus.CREATED).success(true).build()
        String response = mapper.writeValueAsString(service.registerUser(signUpRequest))

        expect:
        mockMvc.perform(MockMvcRequestBuilders.post('/auth/signup')
                .contentType(MediaType.APPLICATION_JSON).content(signUpRequestJSON))
                .andReturn().getResponse().getContentAsString() == response
    }

    void 'should not register'() {
        given:
        service.registerUser(signUpRequest) >> ServerResponse.builder().http(HttpStatus.BAD_REQUEST).success(false).build()
        String response = mapper.writeValueAsString(service.registerUser(signUpRequest))

        expect:
        mockMvc.perform(MockMvcRequestBuilders.post('/auth/signup')
                .contentType(MediaType.APPLICATION_JSON).content(signUpRequestJSON))
                .andReturn().getResponse().getContentAsString() == response
    }
}
