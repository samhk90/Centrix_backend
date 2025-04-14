package com.example.centrix.service;

import com.example.centrix.entity.User;
import com.example.centrix.repository.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private AuthRepository authRepository;
    public boolean login(String username, String password) {
        User user = authRepository.findByEmail(username);

        if (user == null) {
            return false; // User not found
        }

        // Compare raw password with hashed one
        return password.equals(user.getPassword());
    }

//    public void register(String username, String rawPassword) {
//        String hashedPassword = passwordEncoder.encode(rawPassword);
//
//        User newUser = new User();
//        newUser.setUsername(username);
//        newUser.setPassword(hashedPassword);
//
//        userRepository.save(newUser);
//    }
}
