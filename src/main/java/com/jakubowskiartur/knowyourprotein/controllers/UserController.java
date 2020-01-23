package com.jakubowskiartur.knowyourprotein.controllers;

import com.jakubowskiartur.knowyourprotein.payloads.ServerResponse;
import com.jakubowskiartur.knowyourprotein.services.UserService;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

@RestController
@RequestMapping("/data")
@CrossOrigin(origins = "${client.url}")
public class UserController {

    private UserService userService;

    @Inject
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/{id}")
    public ServerResponse<?> retrieveUserByID(@PathVariable Long id) {
        return userService.retrieveByID(id);
    }

    @GetMapping("/users/{username}")
    public ServerResponse<?> retrieveUserByUsername(@PathVariable String username) {
        return userService.retrieveByUsernameOrEmail(username);
    }

    @DeleteMapping("/users/{id}")
    public ServerResponse<?> deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }
}