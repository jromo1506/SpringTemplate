package com.jrrdl.project.services;

import java.util.List;

import com.jrrdl.project.dtos.user.UserRequest;
import com.jrrdl.project.dtos.user.UserResponse;
import com.jrrdl.project.models.User;

public interface UserService {
    

    UserResponse create(UserRequest user);
    UserResponse findById(Long id);
    List<UserResponse> findAll();
    UserResponse update(Long id, UserRequest user);
    void delete(Long id);

    
}
