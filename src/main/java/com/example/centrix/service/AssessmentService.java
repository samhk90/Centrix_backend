package com.example.centrix.service;

import com.example.centrix.dto.*;
import com.example.centrix.dto.request.CalculateResultRequestDTO;
import com.example.centrix.dto.request.CreateUserResponseRequestDTO;
import com.example.centrix.entity.*;
import com.example.centrix.mapper.AssessmentMapper;
import com.example.centrix.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
    @Autowired
    private EnrollmentRepository enrollmentRepository;
    @Autowired
    private ResultsRepository resultsRepository;
    @Autowired
    private AssessmentMapper assessmentMapper;

    public List<AssessmentDTO> getCoursesByCourseId(Integer courseId) {
        List<Assessment> assessments = assessmentRepository.findByCourse_CourseId(courseId);
        return assessmentMapper.toAssessmentDTOList(assessments);
    }

    public List<AssessmentDTO> getAssessmentsByUserId(Integer userId) {
        List<Enrollment> enrollments = enrollmentRepository.findByUser_Uid(userId);
        List<Assessment> assessments = new ArrayList<>();
        for (Enrollment enrollment : enrollments) {
            List<Assessment> courseAssessments = assessmentRepository
                    .findByCourse_CourseId(enrollment.getCourse().getCourseId());
            assessments.addAll(courseAssessments);
        }
        return assessmentMapper.toAssessmentDTOList(assessments);
    }

    public List<QuestionsDTO> getQuestions(Integer assessmentId) {
        List<Questions> questions = questionsRepository.findByAssessment_AssessmentId(assessmentId);
        return assessmentMapper.toQuestionDTOList(questions);
    }

    public List<AssessmentDTO> getAssesmentByassessmentId(Integer assessmentId) {
        List<Assessment> assessments = assessmentRepository.findByAssessmentId(assessmentId);
        return assessmentMapper.toAssessmentDTOList(assessments);
    }

    public UserResponseDTO createUserResponse(CreateUserResponseRequestDTO request) {
        if (request == null || request.getUserId() == null || request.getQuestionId() == null) {
            throw new IllegalArgumentException("UserResponse, userId, and questionId are required");
        }

        UserResponse existingResponse = userResponseRepository
                .findByUserIdAndQuestion_QueId(request.getUserId(), request.getQuestionId());

        Questions question = questionsRepository.findById(request.getQuestionId())
                .orElseThrow(() -> new RuntimeException("Question not found"));

        UserResponse response;
        if (existingResponse != null) {
            response = existingResponse;
        } else {
            response = new UserResponse();
            response.setUserId(request.getUserId());
            response.setQuestion(question);
        }
        response.setSelectedoption(request.getSelectedoption());
        response.setIsresponsecorrect(request.getIsresponsecorrect());

        return assessmentMapper.toUserResponseDTO(userResponseRepository.save(response));
    }

    public ResultsDTO calculateResult(CalculateResultRequestDTO request) {
        if (request == null || request.getUserId() == null || request.getAssessmentId() == null || request.getTimeTaken() == null) {
            throw new IllegalArgumentException("UserId, assessmentId and timeTaken are required");
        }

        List<Questions> questions = questionsRepository.findByAssessment_AssessmentId(request.getAssessmentId());
        Assessment assessment = assessmentRepository.findById(request.getAssessmentId())
                .orElseThrow(() -> new RuntimeException("Assessment not found"));

        int totalMarks = 0;
        int obtainedMarks = 0;

        for (Questions question : questions) {
            UserResponse response = userResponseRepository.findByUserIdAndQuestion_QueId(request.getUserId(), question.getQueId());
            if (response != null && response.getIsresponsecorrect()) {
                obtainedMarks += question.getMarks();
            }
            totalMarks += question.getMarks();
        }

        results result = new results();
        result.setUserId(request.getUserId());
        result.setAssessment(assessment);
        result.setObtainedMarks(obtainedMarks);
        result.setTimeTaken(request.getTimeTaken());

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setFlag(assessment.getFlag());
        userRepository.save(user);

        double percentage = (obtainedMarks * 100.0) / totalMarks;
        result.setStatus(percentage >= assessment.getPassingMarks() ? "PASS" : "FAIL");

        return assessmentMapper.toResultsDTO(resultsRepository.save(result));
    }

    public ResultsDTO getResult(Integer userId, Integer assessmentId) {
        results result = resultsRepository.findByUserIdAndAssessment_AssessmentId(userId, assessmentId);

        return assessmentMapper.toResultsDTO(result);
    }

    public List<ResultsDTO> getAllResults(Integer userId) {
        List<results> results = resultsRepository.findByUserId(userId);
        return assessmentMapper.toResultsDTOList(results);
    }

    public List<UserResponseDTO> getUserResponsesByAssessmentId(Integer assessmentId) {
        List<UserResponse> responses = userResponseRepository.findByQuestion_Assessment_AssessmentId(assessmentId);
        return assessmentMapper.toUserResponseDTOList(responses);
    }
}
