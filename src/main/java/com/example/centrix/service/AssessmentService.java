package com.example.centrix.service;

import com.example.centrix.entity.Assessment;
import com.example.centrix.entity.Course;
import com.example.centrix.entity.Questions;
import com.example.centrix.entity.User;
import com.example.centrix.repository.AssessmentRepository;
import com.example.centrix.repository.AuthRepository;
import com.example.centrix.repository.QuestionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssessmentService {

    @Autowired
    private AssessmentRepository assessmentRepository;

    @Autowired
    private QuestionsRepository questionsRepository;

    public List<Assessment> getCoursesByCourseId(Integer courseId) {
        return assessmentRepository.findByCourse_CourseId(courseId);
    }
    public List<Questions> getQuetions(Integer assessmentId) {
        return questionsRepository.findByAssessment_AssessmentId(assessmentId);
    }
}
