package com.damb.taskmanagment.controller;

import com.damb.taskmanagment.domain.User;
import com.damb.taskmanagment.dto.UserDTO;
import com.damb.taskmanagment.service.UserService;
import com.damb.taskmanagment.service.exceptions.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/v1/user")
public class UserController {
    private UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/create", consumes = "application/json")
    public ResponseEntity<User> createUser(@RequestBody UserDTO userDTO) {
        User user = service.createUser(userDTO);
        return ResponseEntity.created(URI.create(String.format("/api/v1/user/%d", user.getId()))).body(user);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        User foundUser = service.getUserByName(username);
        //todo handle exception user not found
        return ResponseEntity.ok(foundUser);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(service.getAllUsers());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity handleUserNotFoundException() {
        return ResponseEntity.notFound().build();
    }
}
