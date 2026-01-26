package com.jrrdl.project.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import com.jrrdl.project.dtos.auth.AuthResponse;
import com.jrrdl.project.dtos.auth.LoginRequest;
import com.jrrdl.project.dtos.user.UserRequest;
import com.jrrdl.project.dtos.user.UserResponse;
import com.jrrdl.project.services.AuthService;
import com.jrrdl.project.services.UserService;


@RestController
@RequestMapping("/auth")
public class AuthController {
    
    private final AuthService authService;
    private final UserService userService;

    public AuthController(AuthService authService,UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }


    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody UserRequest newUser){
        return ResponseEntity.ok(userService.create(newUser));
    }   
    
}
