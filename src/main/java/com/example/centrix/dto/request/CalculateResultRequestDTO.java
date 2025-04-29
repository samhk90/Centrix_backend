package com.example.centrix.dto.request;

import lombok.Data;

@Data
public class CalculateResultRequestDTO {
    private Integer userId;
    private Integer assessmentId;
    private Long timeTaken;
}