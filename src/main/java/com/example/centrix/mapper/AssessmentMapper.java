package com.example.centrix.mapper;

import com.example.centrix.dto.*;
import com.example.centrix.entity.*;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AssessmentMapper {

    public AssessmentDTO toDTO(Assessment assessment) {
        if (assessment == null) return null;
        
        AssessmentDTO dto = new AssessmentDTO();
        dto.setAssessmentId(assessment.getAssessmentId());
        dto.setNumberOfQuestions(assessment.getNumberOfQuestions());
        dto.setFlag(assessment.getFlag());
        dto.setTotalMarks(assessment.getTotalMarks());
        dto.setDuration(assessment.getDuration());
        dto.setPassingMarks(assessment.getPassingMarks());
        dto.setCourse(toCourseDTO(assessment.getCourse()));
        return dto;
    }

    public List<AssessmentDTO> toAssessmentDTOList(List<Assessment> assessments) {
        return assessments.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public QuestionsDTO toQuestionDTO(Questions question) {
        if (question == null) return null;

        QuestionsDTO dto = new QuestionsDTO();
        dto.setQueId(question.getQueId());
        dto.setQuestion(question.getQuestion());
        dto.setMarks(question.getMarks());
        dto.setOption1(question.getOption1());
        dto.setOption2(question.getOption2());
        dto.setOption3(question.getOption3());
        dto.setOption4(question.getOption4());
        dto.setOption5(question.getOption5());
        dto.setOption6(question.getOption6());
        dto.setCorrectiOption(question.getCorrectiOption());
        dto.setAssessment(toDTO(question.getAssessment()));
        return dto;
    }

    public List<QuestionsDTO> toQuestionDTOList(List<Questions> questions) {
        return questions.stream()
                .map(this::toQuestionDTO)
                .collect(Collectors.toList());
    }

    public UserResponseDTO toUserResponseDTO(UserResponse userResponse) {
        if (userResponse == null) return null;

        UserResponseDTO dto = new UserResponseDTO();
        dto.setUrid(userResponse.getUrid());
        dto.setUserId(userResponse.getUserId());
        dto.setQuestion(toQuestionDTO(userResponse.getQuestion()));
        dto.setSelectedoption(userResponse.getSelectedoption());
        dto.setIsresponsecorrect(userResponse.getIsresponsecorrect());
        return dto;
    }

    public List<UserResponseDTO> toUserResponseDTOList(List<UserResponse> userResponses) {
        return userResponses.stream()
                .map(this::toUserResponseDTO)
                .collect(Collectors.toList());
    }

    public ResultsDTO toResultsDTO(results result) {
        if (result == null) return null;

        ResultsDTO dto = new ResultsDTO();
        dto.setResultId(result.getResultId());
        dto.setUserId(result.getUserId());
        dto.setAssessment(toDTO(result.getAssessment()));
        dto.setObtainedMarks(result.getObtainedMarks());
        dto.setStatus(result.getStatus());
        dto.setTimeTaken(result.getTimeTaken());
        return dto;
    }

    public List<ResultsDTO> toResultsDTOList(List<results> results) {
        return results.stream()
                .map(this::toResultsDTO)
                .collect(Collectors.toList());
    }

    private CourseDTO toCourseDTO(Course course) {
        if (course == null) return null;

        CourseDTO dto = new CourseDTO();
        dto.setCourseId(course.getCourseId());
        dto.setTitle(course.getTitle());
        dto.setDescription(course.getDescription());
        dto.setDuration(course.getDuration());
        dto.setNos(course.getNos());
        return dto;
    }
}