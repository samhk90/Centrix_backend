package com.example.centrix.dto.response;

import com.example.centrix.dto.UserDTO;
import lombok.Data;

@Data
public class JwtAuthResponse {
    private String token;
    private UserDTO user;
}