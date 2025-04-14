package com.example.centrix.repository;

import com.example.centrix.entity.Sections;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SectionRepository extends JpaRepository<Sections, Integer> {
    List<Sections> findByCourse_CourseId(Integer courseId);
}