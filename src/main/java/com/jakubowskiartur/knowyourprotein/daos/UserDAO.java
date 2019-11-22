package com.jakubowskiartur.knowyourprotein.daos;

import com.jakubowskiartur.knowyourprotein.pojos.User;
import com.jakubowskiartur.knowyourprotein.repos.UserRepository;
import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.UUID;

@Component
@Slf4j
public class UserDAO {

    @Inject
    UserRepository userRepository;

    public User retrieveUserByID(UUID id) {
        return userRepository.getOne(id);
    }

//    public User retrieveByUsernameOrEmail(String usernameOrEmail) {
//        return userRepository.findByUsernameOrEmail(usernameOrEmail).orElseThrow(
//                () ->  new UsernameNotFoundException(String.format("Cannot find user with username or e-mail '%s'.", usernameOrEmail))
//        );
//    }

    public void deleteUserByID(UUID id) {
        userRepository.deleteById(id);
        log.info("User with ID = {} deleted successfully.", id);
    }

//    public void deleteUserByUsernameOrEmail(String usernameOrEmail) {
//        User user = retrieveByUsernameOrEmail(usernameOrEmail);
//        userRepository.delete(user);
//        log.info("User '{}' deleted successfully.", usernameOrEmail);
//    }

    public void updatePassword(UUID id, String password) {
        User user = retrieveUserByID(id);
        user.setPassword(password);
        userRepository.save(user);
    }


}
