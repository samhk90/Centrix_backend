package com.example.centrix.repository;

import com.example.centrix.entity.Artifacts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtifactsRepository extends JpaRepository<Artifacts, Integer> {
    List<Artifacts> findByTopics_TopicId(Integer topicId);
}