package com.jrrdl.project.services.impl;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jrrdl.project.dtos.user.UserRequest;
import com.jrrdl.project.dtos.user.UserResponse;
import com.jrrdl.project.enums.Role;
import com.jrrdl.project.models.User;
import com.jrrdl.project.repository.UserRepository;
import com.jrrdl.project.services.UserService;

import jakarta.transaction.Transactional;


@Service
@Transactional
public class UserServiceImpl implements UserService {

   private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder =passwordEncoder;
    }

    public UserResponse userResponseMapper(User user){
        return new UserResponse(
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            user.getRole()
        );
    }

    @Override
    public UserResponse create(UserRequest req) {
        User user  = new User();
        user.setUsername(req.getUsername());
        user.setEmail(req.getEmail());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setRole(Role.USER);
        return userResponseMapper(user);
    }

    @Override
    public UserResponse findById(Long id) {
        User user =  userRepository.findById(id).orElseThrow(
            () -> new RuntimeException("Not found")
        );
        return userResponseMapper(user);
    }

    @Override
    public List<UserResponse> findAll() {
        List<User> users =  userRepository.findAll();
        return users.stream().map(this::userResponseMapper).toList();
    }

    @Override
    public UserResponse update(Long id, UserRequest req) {
       User user =  userRepository.findById(id).orElseThrow(
        () -> new RuntimeException("Not found"));
        user.setUsername(req.getUsername());
        user.setEmail(req.getEmail());
        return userResponseMapper(user);
    }

    @Override
    public void delete(Long id) {
       userRepository.deleteById(id);
    }
    
}
