package com.damb.taskmanagment.converter;

import com.damb.taskmanagment.domain.User;
import com.damb.taskmanagment.dto.UserDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

class UserDTOConverterTest {
    private final String testUsername = "converter";

    private UserDTOConverter converter;

    public UserDTOConverterTest() {
        this.converter = new UserDTOConverter();
    }

    @Test
    void convertUserFromDTOTest() {
        UserDTO dto = getUserDTO();
        User converteredUser = converter.convertUserFromDTO(dto);
        assertEquals(testUsername, converteredUser.getUsername());
    }

    private UserDTO getUserDTO() {
        UserDTO dto = new UserDTO();
        dto.setUsername(testUsername);
        return dto;
    }
}