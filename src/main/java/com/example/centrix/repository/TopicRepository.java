package com.example.centrix.repository;

import com.example.centrix.entity.Topics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicRepository extends JpaRepository<Topics, Integer> {
    List<Topics> findBySection_SectionId(Integer sectionId);
}