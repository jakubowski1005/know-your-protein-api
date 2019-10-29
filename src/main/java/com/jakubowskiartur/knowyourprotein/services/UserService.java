package com.jakubowskiartur.knowyourprotein.services;

import com.jakubowskiartur.knowyourprotein.daos.UserDAO;
import com.jakubowskiartur.knowyourprotein.payloads.ServerResponse;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.UUID;

@Service
public class UserService {

    private UserDAO userDAO;

    @Inject
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public ServerResponse<?> retrieveByID(UUID id) {
        return null;
    }

    public ServerResponse<?> updatePassword(UUID id, String newPassword) {
        return null;
    }

    public ServerResponse<?> deleteUser(UUID id) {
        return null;
    }
}
