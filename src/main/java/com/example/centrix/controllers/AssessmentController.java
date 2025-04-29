package com.example.centrix.controllers;

import com.example.centrix.dto.*;
import com.example.centrix.dto.request.CalculateResultRequestDTO;
import com.example.centrix.dto.request.CreateUserResponseRequestDTO;
import com.example.centrix.service.AssessmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class AssessmentController {
    @Autowired
    private AssessmentService assessmentService;

    @GetMapping("/assessmentinstruction/{courseId}")
    public List<AssessmentDTO> getCoursesByCourseId(@PathVariable Integer courseId) {
        return assessmentService.getCoursesByCourseId(courseId);
    }

    @GetMapping("/assessmentinfo/{assessmentId}")
    public List<AssessmentDTO> getAssessmentById(@PathVariable Integer assessmentId) {
        return assessmentService.getAssesmentByassessmentId(assessmentId);
    }

    @GetMapping("/questions/{assessmentId}/{queId}")
    public List<QuestionsDTO> getQuestions(@PathVariable Integer assessmentId, @PathVariable Integer queId) {
        return assessmentService.getQuetions(assessmentId, queId);
    }

    @PostMapping("/userresponse")
    public UserResponseDTO createUserResponse(@RequestBody CreateUserResponseRequestDTO request) {
        return assessmentService.createUserResponse(request);
    }

    @GetMapping("/calculateresult/{userId}/{assessmentId}")
    public ResultsDTO calculateResult(@PathVariable Integer userId, @PathVariable Integer assessmentId,
                                      @RequestParam Long timetaken) {
        CalculateResultRequestDTO request = new CalculateResultRequestDTO();
        request.setUserId(userId);
        request.setAssessmentId(assessmentId);
        request.setTimeTaken(timetaken);

        return assessmentService.calculateResult(request);
    }

    @GetMapping("/getresults/{userId}/{assessmentId}")
    public ResultsDTO getResult(@PathVariable Integer userId, @PathVariable Integer assessmentId) {
        return assessmentService.getResult(userId, assessmentId);
    }

    @GetMapping("/getAllResults/{userId}")
    public List<ResultsDTO> getAllResults(@PathVariable Integer userId) {
        return assessmentService.getAllResults(userId);
    }

    @GetMapping("/getuserresponse/{assessmentId}")
    public List<UserResponseDTO> getUserResponse(@PathVariable Integer assessmentId) {
        return assessmentService.getUserResponsesByAssessmentId(assessmentId);
    }

    @GetMapping("/assessments/user/{userId}")
    public List<AssessmentDTO> getAssessmentsByUserId(@PathVariable Integer userId) {
        return assessmentService.getAssessmentsByUserId(userId);
    }
}
