package com.damb.taskmanagment.service.users.impl;

import com.damb.taskmanagment.converter.UserDTOConverter;
import com.damb.taskmanagment.domain.User;
import com.damb.taskmanagment.dto.UserDTO;
import com.damb.taskmanagment.repository.UserRepository;
import com.damb.taskmanagment.service.users.UserService;
import com.damb.taskmanagment.service.users.exceptions.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RepositoryUserService implements UserService {

    private final UserRepository repository;
    private final UserDTOConverter userConverter;

    public RepositoryUserService(UserRepository repository, UserDTOConverter userConverter) {
        this.repository = repository;
        this.userConverter = userConverter;
    }

    @Override
    public User createUser(UserDTO userDTO) {
        User user = userConverter.convertUserFromDTO(userDTO);
        return repository.save(user);
    }

    @Override
    public User getUserByName(String username) {
        Optional<User> foundUser = repository.findByUsername(username);
        return foundUser
                .orElseThrow(() -> new UserNotFoundException(String.format("User with name %s not found", username)));
    }

    @Override
    public List<User> getAllUsers() {
        Iterable<User> users = repository.findAll();
        return (List<User>) users;
    }
}
