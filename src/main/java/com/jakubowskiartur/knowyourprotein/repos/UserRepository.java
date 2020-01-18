package com.jakubowskiartur.knowyourprotein.repos;

import com.jakubowskiartur.knowyourprotein.pojos.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends MongoRepository<User, UUID> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    default Optional<User> findByUsernameOrEmail(String usernameOrEmail) {
        Optional<User> found = findByUsername(usernameOrEmail);
        if(found.isPresent()) return found;
        return findByEmail(usernameOrEmail);
    }
}
