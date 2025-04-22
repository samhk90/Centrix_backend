package com.example.centrix.controllers;


import com.example.centrix.entity.Assessment;
import com.example.centrix.entity.Questions;
import com.example.centrix.service.AssessmentService;
import com.example.centrix.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/")
public class AssessmentController {
    @Autowired
    private AssessmentService assessmentService;

    @GetMapping("/assessmentinfo/{courseId}")
    public List<Assessment> getCoursesByCourseId(@PathVariable Integer courseId) {
        return assessmentService.getCoursesByCourseId(courseId);
    }

    @GetMapping("/questions/{assessmentId}")
    public List<Questions> getQuestions(@PathVariable Integer assessmentId) {
        return assessmentService.getQuetions(assessmentId);
    }
}
