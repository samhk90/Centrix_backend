package com.example.centrix.dto;

import lombok.Data;

@Data
public class EnrollmentDTO {
    private Integer enrollId;
    private Integer courseId;
    private Integer userId;
    private String enrollDate;
    private Integer progress;
    private CourseDTO course;
    private UserDTO user;
}