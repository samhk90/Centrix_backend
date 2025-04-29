// src/main/java/com/yourcompany/lms/controller/UserController.java
package com.example.centrix.controllers;

import com.example.centrix.dto.UserDTO;
import com.example.centrix.entity.User;
import com.example.centrix.repository.UserRepository;
import com.example.centrix.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthService authService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // GET all users
    @GetMapping("/users")
    public List<User> getAllUsers() {
        logger.info("Fetching all users");
        List<User> users = userRepository.findAll();
        logger.debug("Found {} users", users.size());
        return users;
    }

    // GET user by ID
    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable Integer id) {
        return authService.userInfo(id);
    }
    @GetMapping("auth/user/{email}")
    public User getUserByEmail(@PathVariable String email) {
        return userRepository.findByEmail(email);
    }
}
