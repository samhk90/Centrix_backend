package com.example.centrix.repository;

import com.example.centrix.entity.Enrollment;
import com.example.centrix.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Integer> {
    List<Enrollment> findByUser(User user);
    List<Enrollment> findByUser_Uid(Integer uid);
}