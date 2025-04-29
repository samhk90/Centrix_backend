package com.example.centrix.dto;

import lombok.Data;

@Data
public class AssessmentDTO {
    private Integer assessmentId;
    private Integer numberOfQuestions;
    private Integer flag;
    private Integer totalMarks;
    private Integer duration;
    private Integer passingMarks;
    private CourseDTO course;
}