package com.jrrdl.project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jrrdl.project.controllers.AuthController;
import com.jrrdl.project.controllers.UserController;
import com.jrrdl.project.dtos.user.UserRequest;
import com.jrrdl.project.dtos.user.UserResponse;
import com.jrrdl.project.security.JwtFilter;
import com.jrrdl.project.security.JwtProvider;
import com.jrrdl.project.services.UserService;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    JwtFilter jwtFilter;

    @MockBean
    JwtProvider jwtProvider;

    @Test
    void shouldGetAllUsers() throws Exception{
        UserResponse user = new UserResponse();
        when(userService.findAll()).thenReturn(List.of(user));

        mockMvc.perform(get("/api/users")).andExpect(status().isOk());
        verify(userService).findAll();
    }

    @Test
    void shouldCreateUser() throws Exception{
        UserRequest req = new UserRequest(null, null, null);
        UserResponse res = new UserResponse();

        when(userService.create(any(UserRequest.class))).thenReturn(res);

         mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk());
            
        verify(userService).create(any(UserRequest.class));

    }

    @Test
    void shouldDeleteUser() throws Exception{
        doNothing().when(userService).delete(1L);

        mockMvc.perform(delete(("/api/users/1"))).andExpect(status().isNoContent());

        verify(userService).delete(1L);
    }

    
    
}
