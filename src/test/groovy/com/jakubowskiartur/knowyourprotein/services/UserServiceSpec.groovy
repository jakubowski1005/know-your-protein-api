package com.jakubowskiartur.knowyourprotein.services

import com.jakubowskiartur.knowyourprotein.payloads.ServerResponse
import com.jakubowskiartur.knowyourprotein.pojos.User
import com.jakubowskiartur.knowyourprotein.repos.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.security.core.userdetails.UsernameNotFoundException
import spock.lang.Specification

class UserServiceSpec extends Specification {

    UserRepository repository
    UserService service
    User user

    void setup() {
        repository = Stub(UserRepository)
        service = new UserService(repository)
        user = new User('username', 'sexystrawberry69@gmail.com', 'pass123')
    }

    void 'should retrieve user by id'() {
        when:
        repository.findById(1L) >> Optional.of(user)
        ServerResponse response = service.retrieveByID(1L)

        then:
        response.getBody() == user
    }

    void 'should retrieve user by username or email'() {
        when:
        repository.findByUsernameOrEmail('sexystrawberry69@gmail.com') >> Optional.of(user)
        ServerResponse response = service.retrieveByUsernameOrEmail('sexystrawberry69@gmail.com')

        then:
        response.getBody() == user
    }

    void 'should delete user by id'() {
        when:
        repository.findById(1L) >> Optional.of(user)
        ServerResponse response = service.deleteUser(1L)

        then:
        response.getHttp() == HttpStatus.OK
        response.getMessage() == "User removed successfully."
    }

    void 'when user cannot be found by id should throw exception'() {
        when:
        repository.findById(1L) >> Optional.empty()
        service.retrieveByID(1L)

        then:
        thrown UsernameNotFoundException
    }

    void 'when user cannot be found by username should throw exception'() {
        when:
        repository.findByUsernameOrEmail('username') >> Optional.empty()
        service.retrieveByUsernameOrEmail('username')

        then:
        thrown UsernameNotFoundException
    }

    void 'when user cannot be deleted by id should throw exception'() {
        when:
        repository.findById(1L) >> Optional.empty()
        service.deleteUser(1L)

        then:
        thrown UsernameNotFoundException
    }
}