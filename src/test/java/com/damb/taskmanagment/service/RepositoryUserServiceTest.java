package com.damb.taskmanagment.service;

import com.damb.taskmanagment.converter.UserDTOConverter;
import com.damb.taskmanagment.domain.User;
import com.damb.taskmanagment.dto.UserDTO;
import com.damb.taskmanagment.repository.UserRepository;
import com.damb.taskmanagment.service.users.impl.RepositoryUserService;
import com.damb.taskmanagment.service.users.UserService;
import com.damb.taskmanagment.service.users.exceptions.UserNotFoundException;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RepositoryUserServiceTest {
    private final static String MOCK_USER_NAME = "mock user";
    private final UserDTO mockUserDTO;
    private final User mockUser;


    private UserService userService;
    private UserRepository mockUserRepository;
    private UserDTOConverter mockUserDTOConverter;

    public RepositoryUserServiceTest() {
        this.mockUser = new User();
        this.mockUser.setUsername(MOCK_USER_NAME);
        this.mockUserDTO = new UserDTO(MOCK_USER_NAME);
        this.mockUserRepository = mock(UserRepository.class);
        this.mockUserDTOConverter = mock(UserDTOConverter.class);
        this.userService = new RepositoryUserService(mockUserRepository, mockUserDTOConverter);
    }

    @Test
    void createUserTest() {
        when(mockUserDTOConverter.convertUserFromDTO(mockUserDTO)).thenReturn(mockUser);
        when(mockUserRepository.save(mockUser)).thenReturn(mockUser);
        User user = userService.createUser(mockUserDTO);
        assertEquals(mockUser.getUsername(), user.getUsername());
    }

    @Test
    void getUserByNameTest() {
        when(mockUserRepository.findByUsername(MOCK_USER_NAME)).thenReturn(Optional.of(mockUser));
        User user = userService.getUserByName(MOCK_USER_NAME);
        assertEquals(mockUser.getUsername(), user.getUsername());
    }

    @Test
    void getUserByNonExistentNameTest() {
        when(mockUserRepository.findByUsername(MOCK_USER_NAME)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.getUserByName(MOCK_USER_NAME));
    }

    @Test
    void getAllUsers() {
        when(mockUserRepository.findAll()).thenReturn(List.of(mockUser));
        List<User> foundUsers = userService.getAllUsers();
        assertEquals(1, foundUsers.size());
        assertEquals(mockUser.getUsername(), foundUsers.get(0).getUsername());
    }
}