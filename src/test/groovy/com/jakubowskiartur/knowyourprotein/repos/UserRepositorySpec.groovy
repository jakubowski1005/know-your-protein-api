package com.jakubowskiartur.knowyourprotein.repos

import com.jakubowskiartur.knowyourprotein.pojos.User
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

import javax.inject.Inject

@ContextConfiguration
@DataJpaTest
class UserRepositorySpec extends Specification {

    @Inject
    TestEntityManager entityManager

    @Inject
    UserRepository repository

    User user

    void setup() {
        user = new User('username', 'email@gmail.com', 'pass1234')
        entityManager.persist(user)
    }

    void 'should find user by username'() {
        when:
        Optional<User> found = repository.findByUsernameOrEmail('username')

        then:
        found.isPresent()
        found.get() == user
        found.get().getUsername() == 'username'
    }

    void 'should find user by email'() {
        when:
        Optional<User> found = repository.findByUsernameOrEmail('email@gmail.com')

        then:
        found.isPresent()
        found.get() == user
        found.get().getUsername() == 'username'
        found.get().getEmail() == 'email@gmail.com'
    }

    void 'should return empty optional if user doesnt exist'() {
        when:
        Optional<User> found = repository.findByUsernameOrEmail('ambakaramba')

        then:
        found == Optional.empty()
    }

    void cleanup() {
        entityManager.clear()
    }

}
