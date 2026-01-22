package com.jrrdl.project.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.jrrdl.project.dtos.auth.AuthResponse;
import com.jrrdl.project.dtos.auth.LoginRequest;
import com.jrrdl.project.security.JwtProvider;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    public AuthService(AuthenticationManager authenticationManager,
                       JwtProvider jwtProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        String token = jwtProvider.generateToken(request.getUsername());
        return new AuthResponse(token);
    }
}
