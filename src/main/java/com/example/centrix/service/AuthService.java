package com.example.centrix.service;

import com.example.centrix.dto.UserDTO;
import com.example.centrix.dto.request.AuthRequestDTO;
import com.example.centrix.entity.User;
import com.example.centrix.mapper.UserMapper;
import com.example.centrix.repository.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private UserMapper userMapper;

    public UserDTO login(AuthRequestDTO request) {
        if (request == null || request.getEmail() == null || request.getPassword() == null) {
            return null;
        }

        User user = authRepository.findByEmail(request.getEmail());

        if (user == null || !request.getPassword().equals(user.getPassword())) {
            return null;
        }

        return userMapper.toDto(user);
    }
}
