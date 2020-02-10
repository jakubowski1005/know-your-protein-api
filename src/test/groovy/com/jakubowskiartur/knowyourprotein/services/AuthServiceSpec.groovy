package com.jakubowskiartur.knowyourprotein.services

import com.jakubowskiartur.knowyourprotein.payloads.CustomTokenResponse
import com.jakubowskiartur.knowyourprotein.payloads.ServerResponse
import com.jakubowskiartur.knowyourprotein.payloads.SignInRequest
import com.jakubowskiartur.knowyourprotein.payloads.SignUpRequest
import com.jakubowskiartur.knowyourprotein.pojos.User
import com.jakubowskiartur.knowyourprotein.repos.UserRepository
import com.jakubowskiartur.knowyourprotein.security.TokenStore
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.crypto.password.PasswordEncoder
import spock.lang.Specification

class AuthServiceSpec extends Specification {

    AuthService service
    UserRepository repository
    AuthenticationManager auth
    TokenStore store
    PasswordEncoder encoder

    SignInRequest signInRequest
    SignUpRequest signUpRequest

    void setup() {
        repository = Stub(UserRepository)
        auth = Mock(AuthenticationManager)
        encoder = Stub(PasswordEncoder)
        store = Stub(TokenStore)
        service = new AuthService(repository, auth, encoder, store)
    }

    void 'should login when using valid credentials'() {
        given:
        signInRequest = new SignInRequest("username", "pass123")
        store.validateToken(_ as String) >> true

        when:
        ServerResponse response = service.authenticateUser(signInRequest)

        then:
        response.getHttp() == HttpStatus.OK
        response.getBody() instanceof CustomTokenResponse
    }

    void 'should not login when using invalid credentials'() {
        given:
        signInRequest = new SignInRequest("username", "pass123")
        store.validateToken(_ as String) >> false

        when:
        ServerResponse response = service.authenticateUser(signInRequest)

        then:
        response.getHttp() == HttpStatus.UNAUTHORIZED
        !response.getSuccess()
    }

    void 'should register when using valid credentials'() {
        given:
        signUpRequest = new SignUpRequest("username", "barbie@op.pl", 'pass124')
        repository.existsByUsername(_ as String) >> false
        repository.existsByEmail(_ as String) >> false
        repository.save(_ as User) >> null
        encoder.encode(_ as String) >> signUpRequest.getPassword()

        when:
        ServerResponse response = service.registerUser(signUpRequest)

        then:
        response.getHttp() == HttpStatus.CREATED
        response.getSuccess()
    }

    void 'should not register when using invalid username'() {
        given:
        signUpRequest = new SignUpRequest("username", "barbie@op.pl", 'pass124')
        repository.existsByUsername(_ as String) >> true
        repository.existsByEmail(_ as String) >> false

        when:
        ServerResponse response = service.registerUser(signUpRequest)

        then:
        response.getHttp() == HttpStatus.BAD_REQUEST
        !response.getSuccess()
        response.getMessage() == "Username already taken!"
    }

    void 'should not register when using invalid email'() {
        given:
        signUpRequest = new SignUpRequest("username", "barbie@op.pl", 'pass124')
        repository.existsByUsername(_ as String) >> false
        repository.existsByEmail(_ as String) >> true

        when:
        ServerResponse response = service.registerUser(signUpRequest)

        then:
        response.getHttp() == HttpStatus.BAD_REQUEST
        !response.getSuccess()
        response.getMessage() == "E-mail already in use!"
    }
}
