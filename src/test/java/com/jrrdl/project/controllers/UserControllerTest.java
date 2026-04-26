// File: UserControllerTest.java

package com.jrrdl.project.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jrrdl.project.dtos.user.UserRequest;
import com.jrrdl.project.dtos.user.UserResponse;
import com.jrrdl.project.services.UserService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    @DisplayName("Debe crear usuario")
    void shouldCreateUser() throws Exception {

        UserRequest request = new UserRequest();
        request.setUsername("jorge");
        request.setPassword("123");

        UserResponse response = new UserResponse();
        response.setId(1L);
        response.setUsername("jorge");

        when(userService.create(any(UserRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("jorge"));
    }

    @Test
    @DisplayName("Debe listar usuarios")
    void shouldGetUsers() throws Exception {

        UserResponse user = new UserResponse();
        user.setId(1L);
        user.setUsername("jorge");

        when(userService.findAll()).thenReturn(List.of(user));

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].username").value("jorge"));
    }

    @Test
    @DisplayName("Debe obtener usuario por id")
    void shouldGetUserById() throws Exception {

        UserResponse user = new UserResponse();
        user.setId(1L);
        user.setUsername("jorge");

        when(userService.findById(1L)).thenReturn(user);

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("jorge"));
    }

    @Test
    @DisplayName("Debe actualizar usuario")
    void shouldUpdateUser() throws Exception {

        UserRequest request = new UserRequest();
        request.setUsername("nuevo");

        UserResponse response = new UserResponse();
        response.setId(1L);
        response.setUsername("nuevo");

        when(userService.update(eq(1L), any(UserRequest.class)))
                .thenReturn(response);

        mockMvc.perform(put("/api/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("nuevo"));
    }

    @Test
    @DisplayName("Debe eliminar usuario")
    void shouldDeleteUser() throws Exception {

        doNothing().when(userService).delete(1L);

        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isNoContent());
    }
}