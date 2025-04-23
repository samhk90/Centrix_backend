package com.example.centrix.repository;

import com.example.centrix.entity.Assessment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssessmentRepository  extends JpaRepository<Assessment, Integer> {
    List<Assessment> findByCourse_CourseId(Integer courseId);

    List<Assessment> findByAssessmentId(Integer assessmentId);
}
