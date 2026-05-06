package com.jrrdl.project.services.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import com.jrrdl.project.dtos.user.UserRequest;
import com.jrrdl.project.enums.Role;
import com.jrrdl.project.models.User;
import com.jrrdl.project.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // init mocks
    }

    @Test
    void shouldCreateUser() {
        UserRequest req = new UserRequest("john", "john@mail.com", "123");

        when(passwordEncoder.encode("123")).thenReturn("encoded123");

        User saved = new User();
        saved.setId(1L);
        saved.setUsername("john");
        saved.setEmail("john@mail.com");
        saved.setPassword("encoded123");
        saved.setRole(Role.USER);

        when(userRepository.save(any(User.class))).thenReturn(saved);

        var result = userService.create(req);

        assertEquals("john", result.getUsername());
        assertEquals("john@mail.com", result.getEmail());

        verify(userRepository).save(any(User.class)); // 👈 verifica persistencia
        verify(passwordEncoder).encode("123"); // 👈 verifica encriptado
    }

    @Test
    void shouldFindById() {
        User user = new User();
        user.setId(1L);
        user.setUsername("john");
        user.setEmail("john@mail.com");
        user.setRole(Role.USER);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        var result = userService.findById(1L);

        assertEquals("john", result.getUsername());
    }

    @Test
    void shouldThrowWhenUserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            userService.findById(1L);
        }); // 👈 validas excepción
    }

    @Test
    void shouldFindAll() {
        User user = new User();
        user.setId(1L);
        user.setUsername("john");
        user.setEmail("john@mail.com");
        user.setRole(Role.USER);

        when(userRepository.findAll()).thenReturn(List.of(user));

        var result = userService.findAll();

        assertEquals(1, result.size());
    }

    @Test
    void shouldUpdateUser() {
        User existing = new User();
        existing.setId(1L);
        existing.setUsername("old");
        existing.setEmail("old@mail.com");

        UserRequest req = new UserRequest("new", "new@mail.com", "123");

        when(userRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(userRepository.save(any(User.class))).thenReturn(existing);

        var result = userService.update(1L, req);

        assertEquals("new", result.getUsername());
        assertEquals("new@mail.com", result.getEmail());

        verify(userRepository).save(existing); // 👈 valida update
    }

    @Test
    void shouldDeleteUser() {
        userService.delete(1L);

        verify(userRepository).deleteById(1L); // 👈 valida delete
    }
}