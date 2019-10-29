package com.jakubowskiartur.knowyourprotein.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.jakubowskiartur.knowyourprotein.payloads.SignInRequest
import com.jakubowskiartur.knowyourprotein.payloads.SignUpRequest
import com.jakubowskiartur.knowyourprotein.services.AuthService
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

class AuthControllerSpec extends Specification {

    AuthController controller
    AuthService service
    MockMvc mockMvc
    ObjectMapper mapper

    SignInRequest signInRequest
    SignUpRequest signUpRequest

    String signInRequestJSON
    String signUpRequestJSON

    void setup() {
        service = Stub(AuthService)
        controller = new AuthController([authService: service])
        mockMvc = MockMvcBuilders.standaloneSetup(controller).alwaysDo(MockMvcResultHandlers.print()).build()

        signInRequest = new SignInRequest([usernameOrEmail: 'username', password: 'password'])
        //signInRequest = new SignInRequest("username", "pass1234")
        signUpRequest = new SignUpRequest([username: 'username', email: 'email', password: 'password'])

        signInRequestJSON = mapper.writeValueAsString(signInRequest)
        signUpRequestJSON = mapper.writeValueAsString(signUpRequest)
    }

}
