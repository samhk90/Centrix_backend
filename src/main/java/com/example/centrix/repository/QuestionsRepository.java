package com.example.centrix.repository;

import com.example.centrix.entity.Questions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface QuestionsRepository extends JpaRepository<Questions, Integer> {
    List<Questions> findByAssessment_AssessmentIdAndQueId(Integer assessmentId, Integer queId);

    List<Questions> findByAssessment_AssessmentId(Integer assessmentId);
}
