package com.example.centrix.dto.request;

import lombok.Data;

@Data
public class CreateUserResponseRequestDTO {
    private Integer userId;
    private Integer questionId;
    private String selectedOption;
    private Boolean isResponseCorrect;
}