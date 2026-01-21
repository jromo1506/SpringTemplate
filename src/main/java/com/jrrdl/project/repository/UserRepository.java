package com.jrrdl.project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jrrdl.project.models.User;
import java.util.List;


public interface UserRepository  extends JpaRepository<User,Long>{
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    boolean existsByUsername(String Username);
}
