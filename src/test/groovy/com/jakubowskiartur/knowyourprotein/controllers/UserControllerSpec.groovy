package com.jakubowskiartur.knowyourprotein.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.jakubowskiartur.knowyourprotein.payloads.ServerResponse
import com.jakubowskiartur.knowyourprotein.pojos.User
import com.jakubowskiartur.knowyourprotein.services.UserService
import org.springframework.http.HttpStatus
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

class UserControllerSpec extends Specification {

    UserController controller
    MockMvc mockMvc
    UserService service
    ObjectMapper objectMapper = new ObjectMapper()

    User user1
    String user1Json

    void setup() {
        service = Mock(UserService)
        controller = new UserController(service)
        mockMvc = MockMvcBuilders.standaloneSetup(controller).alwaysDo(MockMvcResultHandlers.print()).build()

        user1 = new User([username: 'user1', email: 'email@gmail.com', password: 'pass'])
        user1Json = objectMapper.writeValueAsString(user1)
    }

    void 'should return user by id'() {
        given:
        ServerResponse response = ServerResponse.builder().http(HttpStatus.OK).success(true).message("User data loaded successfully.").body(user1).build()
        1 * service.retrieveByID(1) >> response
        String json = objectMapper.writeValueAsString(response)

        expect:
        mockMvc.perform(MockMvcRequestBuilders.get('/data/users/1'))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(json))
    }

    void 'should return user by username or email'() {
        given:
        ServerResponse response = ServerResponse.builder().http(HttpStatus.OK).success(true).message("User data loaded successfully.").body(user1).build()
        1 * service.retrieveByUsernameOrEmail('user1') >> response
        String json = objectMapper.writeValueAsString(response)

        expect:
        mockMvc.perform(MockMvcRequestBuilders.get('/data/users?username=user1'))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(json))
    }

    void 'should delete user'() {
        given:
        ServerResponse response = ServerResponse.builder().success(true).message("User removed successfully.").http(HttpStatus.OK).build()
        1 * service.deleteUser(1) >> response
        String json = objectMapper.writeValueAsString(response)

        expect:
        mockMvc.perform(MockMvcRequestBuilders.delete('/data/users/1'))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(json))
    }
}
