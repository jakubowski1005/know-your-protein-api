package com.jakubowskiartur.knowyourprotein.daos

import com.jakubowskiartur.knowyourprotein.pojos.User
import com.jakubowskiartur.knowyourprotein.repos.UserRepository
import org.springframework.security.core.userdetails.UsernameNotFoundException
import spock.lang.Specification

class UserDAOSpec extends Specification {

    UserRepository repository
    UserDAO dao

    User user
    UUID id

    void setup() {
        repository = Stub(UserRepository)
        dao = new UserDAO([userRepository: repository])

        user = new User('username', 'email@gmail.com', 'pass1234')
        id = UUID.randomUUID()
    }

    void 'should retrieve user by id'() {
        when:
        repository.getOne(id) >> user
        User found = dao.retrieveUserByID(id)

        then:
        found == user
    }

    void 'should retrieve user by username'() {
        when:
        repository.findByUsernameOrEmail((String)_) >> Optional.of(user)
        User found = dao.retrieveByUsernameOrEmail('username')

        then:
        found == user
    }

    void 'should retrieve user by email'() {
        when:
        repository.findByUsernameOrEmail('email@gmail.com') >> Optional.of(user)
        User found = dao.retrieveByUsernameOrEmail('email@gmail.com')

        then:
        found == user
    }

    void 'should return null if user doesnt exist'() {
        when:
        repository.getOne(id) >> null
        User found = dao.retrieveUserByID(id)

        then:
        found == null
    }

    void 'should throw UsernameNotFoundException if user doesnt exist'() {
        when:
        repository.findByUsernameOrEmail('notExistingUsername') >> Optional.empty()
        dao.retrieveByUsernameOrEmail('notExistingUsername')

        then:
        final UsernameNotFoundException exception = thrown()
        exception.getMessage() == "Cannot find user with username or e-mail 'notExistingUsername'."
    }
}
