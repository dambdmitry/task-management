package com.damb.taskmanagment.controller;

import com.damb.taskmanagment.domain.User;
import com.damb.taskmanagment.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user")
public class UserController {
    private UserRepository repository;

    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/create", consumes = "application/json")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User newUser = repository.save(user);
        return ResponseEntity.ok(newUser);
    }
}
