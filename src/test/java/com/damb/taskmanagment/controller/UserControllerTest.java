package com.damb.taskmanagment.controller;

import com.damb.taskmanagment.domain.User;
import com.damb.taskmanagment.dto.UserDTO;
import com.damb.taskmanagment.service.UserService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserControllerTest {

    private final String TEST_USERNAME = "test user";
    private final User mockUser;
    private final UserDTO mockUserDTO;
    private final String jsonUserDTO;
    private final String jsonUser;

    private MockMvc mockMvc;
    private UserService mockUserService;
    private Gson gson;

    public UserControllerTest() {
        this.gson = new GsonBuilder().create();
        this.mockUserService = mock(UserService.class);
        this.mockMvc = MockMvcBuilders.standaloneSetup(new UserController(mockUserService)).build();

        this.mockUser = new User();
        mockUser.setUsername(TEST_USERNAME);
        this.mockUserDTO = new UserDTO(TEST_USERNAME);
        this.jsonUserDTO = gson.toJson(mockUserDTO);
        this.jsonUser = gson.toJson(mockUser);
    }

    @Test
    void createUser() throws Exception{
        when(mockUserService.createUser(mockUserDTO)).thenReturn(mockUser);
        this.mockMvc
                .perform(post("/api/v1/user/create")
                        .content(jsonUserDTO)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(jsonUser));
    }

    @Test
    void getUserByUsername() throws Exception {
        when(mockUserService.getUserByName(TEST_USERNAME)).thenReturn(mockUser);
        this.mockMvc
                .perform(MockMvcRequestBuilders.get(String.format("/api/v1/user/%s", TEST_USERNAME)))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonUser));
    }

    @Test
    void getAllUsers() throws Exception {
        when(mockUserService.getAllUsers()).thenReturn(Collections.emptyList());
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/api/v1/user/"))
                .andExpect(status().isOk());
    }
}