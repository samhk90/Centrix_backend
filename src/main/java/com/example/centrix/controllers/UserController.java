// src/main/java/com/yourcompany/lms/controller/UserController.java
package com.example.centrix.controllers;

import com.example.centrix.dto.UserDTO;
import com.example.centrix.entity.User;
import com.example.centrix.mapper.UserMapper;
import com.example.centrix.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserMapper userMapper;
    
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @GetMapping("/users")
    public List<UserDTO> getAllUsers() {
        logger.info("Fetching all users");
        List<User> users = userRepository.findAll();
        logger.debug("Found {} users", users.size());
        return userMapper.toUserDtoList(users);
    }

    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return userMapper.toDto(user);
    }

    @GetMapping("auth/user/{email}")
    public UserDTO getUserByEmail(@PathVariable String email) {
        User user = userRepository.findByEmail(email);
        return userMapper.toDto(user);
    }
}
