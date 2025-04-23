package com.example.centrix.service;

import com.example.centrix.entity.*;
import com.example.centrix.repository.AssessmentRepository;
import com.example.centrix.repository.AuthRepository;
import com.example.centrix.repository.QuestionsRepository;
import com.example.centrix.repository.UserResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssessmentService {

    @Autowired
    private AssessmentRepository assessmentRepository;

    @Autowired
    private QuestionsRepository questionsRepository;
    @Autowired
    private UserResponseRepository userResponseRepository;

    public List<Assessment> getCoursesByCourseId(Integer courseId) {
        return assessmentRepository.findByCourse_CourseId(courseId);
    }
    public List<Questions> getQuetions(Integer assessmentId, Integer queId) {
        Integer newQueId=queId+1;
        return questionsRepository.findByAssessment_AssessmentIdAndQueId(assessmentId,newQueId);
    }
    public List<Assessment> getAssesmentByassessmentId(Integer assessmentId) {
        return assessmentRepository.findByAssessmentId(assessmentId);
    }
    public UserResponse createUserResponse(UserResponse userResponse) {
        return userResponseRepository.save(userResponse);
    }
}
