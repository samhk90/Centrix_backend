package com.example.centrix.service;

import com.example.centrix.entity.Enrollment;
import com.example.centrix.repository.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.time.LocalDate;

@Service
public class EnrollmentService {
    @Autowired
    private EnrollmentRepository enrollmentRepository;

    public List<Enrollment> getEnrollmentsByUserId(Integer userId) {
        return enrollmentRepository.findByUser_Uid(userId);
    }
    public Enrollment createEnrollment(Enrollment enrollment) {


        // Set default values if not provided
        if (enrollment.getEnrollDate() == null) {
            enrollment.setEnrollDate(LocalDate.now().toString());
        }
        if (enrollment.getProgress() == null) {
            enrollment.setProgress(0);
        }

        Enrollment savedEnrollment = enrollmentRepository.save(enrollment);
        System.out.println("Saved enrollment: " + savedEnrollment);
        return savedEnrollment;
    }
}