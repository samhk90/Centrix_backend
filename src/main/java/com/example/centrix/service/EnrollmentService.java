package com.example.centrix.service;

import com.example.centrix.entity.Enrollment;
import com.example.centrix.entity.User;
import com.example.centrix.entity.Course;
import com.example.centrix.repository.EnrollmentRepository;
import com.example.centrix.repository.UserRepository;
import com.example.centrix.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.time.LocalDate;

@Service
public class EnrollmentService {
    private static final Logger logger = LoggerFactory.getLogger(EnrollmentService.class);
    
    @Autowired
    private EnrollmentRepository enrollmentRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private CourseRepository courseRepository;

    public List<Enrollment> getEnrollmentsByUserId(Integer userId) {
        logger.debug("Fetching enrollments for user ID: {}", userId);
        List<Enrollment> enrollments = enrollmentRepository.findByUser_Uid(userId);
        logger.debug("Found {} enrollments for user ID: {}", enrollments.size(), userId);
        return enrollments;
    }

    @Transactional
    public Enrollment createEnrollment(Enrollment enrollment) {
        logger.debug("Creating new enrollment with data: {}", enrollment);

        Integer userId = enrollment.getUserId();
        Integer courseId = enrollment.getCourseId();

        if (userId == null || courseId == null) {
            throw new IllegalArgumentException("User ID and Course ID are required");
        }

        // Load the actual entities
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Course course = courseRepository.findById(courseId)
            .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        // Create new enrollment instance
        Enrollment newEnrollment = new Enrollment();
        newEnrollment.setUser(user);
        newEnrollment.setCourse(course);
        newEnrollment.setEnrollDate(LocalDate.now().toString());
        newEnrollment.setProgress(0);

        try {
            return enrollmentRepository.save(newEnrollment);
        } catch (Exception e) {
            logger.error("Error creating enrollment: ", e);
            throw new RuntimeException("Failed to create enrollment", e);
        }
    }
}