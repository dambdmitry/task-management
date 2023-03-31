package com.damb.taskmanagment.service.users;

import com.damb.taskmanagment.domain.User;
import com.damb.taskmanagment.dto.UserDTO;

import java.util.List;

public interface UserService {
    User createUser(UserDTO userDTO);

    User getUserByName(String username);

    List<User> getAllUsers();
}
