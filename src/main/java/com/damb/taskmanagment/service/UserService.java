package com.damb.taskmanagment.service;

import com.damb.taskmanagment.domain.User;
import com.damb.taskmanagment.dto.UserDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User createUser(UserDTO userDTO);
    User getUserByName(String username);
    List<User> getAllUsers();
}
