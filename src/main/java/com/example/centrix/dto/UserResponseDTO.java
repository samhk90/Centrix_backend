package com.example.centrix.dto;

import lombok.Data;

@Data
public class UserResponseDTO {
    private Integer urid;
    private Integer userId;
    private QuestionsDTO question;
    private String selectedoption;
    private Boolean isresponsecorrect;
}