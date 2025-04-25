package com.example.centrix.service;

import com.example.centrix.entity.*;
import com.example.centrix.repository.*;
import org.apache.logging.log4j.Logger;
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
        UserResponse existingResponse = userResponseRepository
                .findByUserIdAndQuetionId(userResponse.getUserId(), userResponse.getQuetionId());
        System.out.println("hello i am here "+existingResponse);
        if (existingResponse != null) {
            existingResponse.setSelectedoption(userResponse.getSelectedoption());
            existingResponse.setIsresponsecorrect(userResponse.getIsresponsecorrect());
            return userResponseRepository.save(existingResponse);
        }

        // Save new response
        return userResponseRepository.save(userResponse);
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
            UserResponse response = userResponseRepository.findByUserIdAndQuetionId(userId, question.getQueId());
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

        double percentage = (obtainedMarks * 100.0) / totalMarks;
        result.setStatus(percentage >= assessment.getPassingMarks() ? "PASS" : "FAIL");

        return resultsRepository.save(result);
    }
}
