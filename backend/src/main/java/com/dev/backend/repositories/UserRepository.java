package com.dev.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.backend.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    
}
