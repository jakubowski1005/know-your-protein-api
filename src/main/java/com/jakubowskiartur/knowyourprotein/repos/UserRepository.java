package com.jakubowskiartur.knowyourprotein.repos;

import com.jakubowskiartur.knowyourprotein.pojos.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    default Optional<User> findByUsernameOrEmail(String usernameOrEmail) {
        Optional<User> found = findByUsername(usernameOrEmail);
        if(found.isPresent()) return found;
        return findByEmail(usernameOrEmail);
    }
}
