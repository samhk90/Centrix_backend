package com.example.centrix.controllers;

import com.example.centrix.dto.UserDTO;
import com.example.centrix.dto.request.AuthRequestDTO;
import com.example.centrix.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody AuthRequestDTO request) {
        UserDTO user = authService.login(request);
        if (user == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(user);
    }
}
