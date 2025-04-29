package com.example.centrix.dto;

import lombok.Data;

@Data
public class QuestionsDTO {
    private Integer queId;
    private String question;
    private Integer marks;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String option5;
    private String option6;
    private String correctiOption;
    private AssessmentDTO assessment;
}