package com.dev.backend.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dev.backend.model.User;
import com.dev.backend.repositories.UserRepository;

@Service
public class UserServices {
    
    @Autowired
    private UserRepository repo;

    public User insert(User obj) {
        return repo.save(obj);
    }

    public List<User> findAll() {
        return repo.findAll();
    }

}