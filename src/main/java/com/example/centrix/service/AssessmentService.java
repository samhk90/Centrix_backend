package com.example.centrix.service;

import com.example.centrix.entity.*;
import com.example.centrix.repository.*;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Service
public class AssessmentService {

    @Autowired
    private AssessmentRepository assessmentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private QuestionsRepository questionsRepository;
    @Autowired
    private UserResponseRepository userResponseRepository;

    public List<Assessment> getCoursesByCourseId(Integer courseId) {
        return assessmentRepository.findByCourse_CourseId(courseId);
    }



    @Autowired
    private EnrollmentRepository enrollmentRepository; // Add this repository

    public List<Assessment> getAssessmentsByUserId(Integer userId) {
        // Get user's enrollments
        List<Enrollment> enrollments = enrollmentRepository.findByUser_Uid(userId);

        // Get assessments for enrolled courses
        List<Assessment> assessments = new ArrayList<>();
        for (Enrollment enrollment : enrollments) {
            List<Assessment> courseAssessments = assessmentRepository
                    .findByCourse_CourseId(enrollment.getCourse().getCourseId());
            assessments.addAll(courseAssessments);
        }

        return assessments;
    }

    public List<Questions> getQuetions(Integer assessmentId, Integer queId) {
        Integer newQueId=queId+1;
        return questionsRepository.findByAssessment_AssessmentIdAndQueId(assessmentId,newQueId);
    }
    public List<Assessment> getAssesmentByassessmentId(Integer assessmentId) {
        return assessmentRepository.findByAssessmentId(assessmentId);
    }
    public UserResponse createUserResponse(UserResponse userResponse) {
        // Validate input

        if (userResponse == null || userResponse.getUserId() == null || userResponse.getQuestion() == null) {
            throw new IllegalArgumentException("UserResponse, userId, and question are required");
        }

        // Find existing response if any
        UserResponse existingResponse = null;
        try {
            existingResponse = userResponseRepository
                    .findByUserIdAndQuestion_QueId(userResponse.getUserId(), userResponse.getQuestion().getQueId());
        } catch (Exception e) {
            // Log error if needed
        }

        if (existingResponse != null) {
            // Update existing response
            existingResponse.setSelectedoption(userResponse.getSelectedoption());
            existingResponse.setIsresponsecorrect(userResponse.getIsresponsecorrect());
            return userResponseRepository.save(existingResponse);
        }

        // Validate question exists
        Questions question = questionsRepository.findById(userResponse.getQuestion().getQueId())
                .orElseThrow(() -> new RuntimeException("Question not found"));

        // Create new response with validated question
        UserResponse newResponse = new UserResponse();
        newResponse.setUserId(userResponse.getUserId());
        newResponse.setQuestion(question);
        newResponse.setSelectedoption(userResponse.getSelectedoption());
        newResponse.setIsresponsecorrect(userResponse.getIsresponsecorrect());

        return userResponseRepository.save(newResponse);
    }

    @Autowired
    private ResultsRepository resultsRepository;

    public results calculateResult(Integer userId, Integer assessmentId, Long timeTaken) {
        List<Questions> questions = questionsRepository.findByAssessment_AssessmentId(assessmentId);
        Assessment assessment = assessmentRepository.findById(assessmentId)
                .orElseThrow(() -> new RuntimeException("Assessment not found"));

        int totalMarks = 0;
        int obtainedMarks = 0;

        for (Questions question : questions) {
            UserResponse response = userResponseRepository.findByUserIdAndQuestion_QueId(userId, question.getQueId());
            if (response != null && response.getIsresponsecorrect()) {
                obtainedMarks += question.getMarks();
            }
            totalMarks += question.getMarks();
        }

        results result = new results();
        result.setUserId(userId);
        result.setAssessment(assessment);
        result.setObtainedMarks(obtainedMarks);
        result.setTimeTaken(timeTaken);

        User user=userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        user.setFlag(assessment.getFlag());
        userRepository.save(user);
        double percentage = (obtainedMarks * 100.0) / totalMarks;
        System.out.println(obtainedMarks);
        result.setStatus(percentage >= assessment.getPassingMarks() ? "PASS" : "FAIL");

        return resultsRepository.save(result);
    }

    public results getResult(Integer userId, Integer assessmentId) {
        results result = resultsRepository.findByUserIdAndAssessment_AssessmentId(userId, assessmentId);
        if (result == null) {
            throw new RuntimeException("Result not found for user " + userId + " and assessment " + assessmentId);
        }
        return result;
    }
    public List<results> getAllResults(Integer userId){
        return  resultsRepository.findByUserId(userId);
    }

    public List<UserResponse> getUserResponsesByAssessmentId(Integer assessmentId) {
        return userResponseRepository.findByQuestion_Assessment_AssessmentId(assessmentId);
    }
}
