package com.jrrdl.project.dtos.user;

import com.jrrdl.project.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class UserResponse {

    private Long id;
    private String username;
    private String email;
    private Role role;

}
