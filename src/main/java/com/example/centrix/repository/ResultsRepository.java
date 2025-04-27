package com.example.centrix.repository;

import com.example.centrix.entity.results;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ResultsRepository extends JpaRepository<results, Integer> {
    results findByUserIdAndAssessment_AssessmentId(Integer userId, Integer assessmentId);
    List<results> findByUserId(Integer userId);
}