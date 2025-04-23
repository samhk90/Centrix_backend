package com.example.centrix.controllers;


import com.example.centrix.entity.Assessment;
import com.example.centrix.entity.Questions;
import com.example.centrix.entity.UserResponse;
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

    @GetMapping("/assessmentinstruction/{courseId}")
    public List<Assessment> getCoursesByCourseId(@PathVariable Integer courseId) {
        return assessmentService.getCoursesByCourseId(courseId);
    }
    @GetMapping("/assessmentinfo/{assessmentId}")
    public List<Assessment> getAssessmentById(@PathVariable Integer assessmentId) {
        return assessmentService.getAssesmentByassessmentId(assessmentId);
    }
    @GetMapping("/questions/{assessmentId}/{queId}")
    public List<Questions> getQuestions(@PathVariable Integer assessmentId,@PathVariable Integer queId) {
        return assessmentService.getQuetions(assessmentId,queId);
    }
    @PostMapping("/userresponse")
    public UserResponse createUserResponse(@RequestBody UserResponse userreponse) {
        return assessmentService.createUserResponse(userreponse);
    }
}
