// File: AuthControllerTest.java

package com.jrrdl.project.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jrrdl.project.dtos.auth.AuthResponse;
import com.jrrdl.project.dtos.auth.LoginRequest;
import com.jrrdl.project.dtos.user.UserRequest;
import com.jrrdl.project.dtos.user.UserResponse;
import com.jrrdl.project.services.AuthService;
import com.jrrdl.project.services.UserService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
// File: AuthControllerTest.java

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthService authService;

    @MockBean
    private UserService userService;

    @Test
    @DisplayName("Login correcto")
    void shouldLoginSuccessfully() throws Exception {

        LoginRequest request = new LoginRequest();
        request.setUsername("jorge");
        request.setPassword("123456");

        AuthResponse response = new AuthResponse("jwt-token");

        when(authService.login(request)).thenReturn(response);

        // File: AuthControllerTest.java

       
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk());
            }

    @Test
    @DisplayName("Registro correcto")
    void shouldRegisterSuccessfully() throws Exception {

        UserRequest request = new UserRequest();
        request.setUsername("jorge");
        request.setPassword("123456");

        UserResponse response = new UserResponse();
        response.setId(1L);
        response.setUsername("jorge");

// File: AuthControllerTest.java

    when(userService.create(any(UserRequest.class))).thenReturn(response);
      mockMvc.perform(post("/auth/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string(org.hamcrest.Matchers.containsString("jorge")));
}
}