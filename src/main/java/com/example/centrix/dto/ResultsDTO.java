package com.example.centrix.dto;

import lombok.Data;

@Data
public class ResultsDTO {
    private Integer resultId;
    private Integer userId;
    private AssessmentDTO assessment;
    private Integer obtainedMarks;
    private String status;
    private Long timeTaken;
}