package com.jrrdl.project.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.jrrdl.project.controllers.AuthController;
import com.jrrdl.project.dtos.auth.AuthResponse;
import com.jrrdl.project.dtos.auth.LoginRequest;
import com.jrrdl.project.dtos.user.UserRequest;
import com.jrrdl.project.dtos.user.UserResponse;
import com.jrrdl.project.services.AuthService;
import com.jrrdl.project.services.UserService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AuthControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    AuthService authService;


    @MockBean
    UserService userService;

    @Test
    void shouldLogin() throws Exception{
        LoginRequest req = new LoginRequest();
        AuthResponse res = new AuthResponse("test-token");

        when(authService.login(any(LoginRequest.class))).thenReturn(res);

        mockMvc.perform(
                post("/auth/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(req))
        )
        .andExpect(status().isOk());
    }

    
}
