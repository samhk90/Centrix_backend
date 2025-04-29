package com.example.centrix.dto;

import lombok.Data;
import java.util.List;

@Data
public class CourseDTO {
    private Integer courseId;
    private String title;
    private String description;
    private String duration;
    private Integer nos;
    private ChapterDTO chapter;
    private TrainerDTO trainer;
    private List<SectionDTO> sections;
}