package com.example.centrix.service;

import com.example.centrix.dto.EnrollmentDTO;
import com.example.centrix.dto.CourseDTO;
import com.example.centrix.entity.Enrollment;
import com.example.centrix.entity.User;
import com.example.centrix.entity.Course;
import com.example.centrix.mapper.EnrollmentMapper;
import com.example.centrix.repository.EnrollmentRepository;
import com.example.centrix.repository.UserRepository;
import com.example.centrix.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
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

    @Autowired
    private EnrollmentMapper enrollmentMapper;

    @Autowired
    private CourseService courseService;

    @Transactional(readOnly = true)
    public List<EnrollmentDTO> getEnrollmentsByUserId(Integer userId) {
        logger.debug("Fetching enrollments for user ID: {}", userId);
        List<Enrollment> enrollments = enrollmentRepository.findByUser_Uid(userId);
        
        // Get completed courses
        List<CourseDTO> completedCourses = courseService.getCompletedCourses(userId);
        Set<Integer> completedCourseIds = completedCourses.stream()
                .map(CourseDTO::getCourseId)
                .collect(Collectors.toSet());

        // Filter out completed courses
        List<Enrollment> filteredEnrollments = enrollments.stream()
                .filter(enrollment -> !completedCourseIds.contains(enrollment.getCourseId()))
                .collect(Collectors.toList());

        return enrollmentMapper.toEnrollmentDtoList(filteredEnrollments);
    }

    @Transactional(readOnly = true)
    public Boolean isEnrolled(Integer userId, Integer courseId) {
        logger.debug("Checking if user ID: {} is enrolled in course ID: {}", userId, courseId);
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Course course = courseRepository.findById(courseId)
            .orElseThrow(() -> new IllegalArgumentException("Course not found"));
        return enrollmentRepository.existsByUserAndCourse(user, course);
    }

    @Transactional(readOnly = true)
    public Integer getProgress(Integer userId, Integer courseId) {
        logger.debug("Fetching progress for user ID: {} in course ID: {}", userId, courseId);
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Course course = courseRepository.findById(courseId)
            .orElseThrow(() -> new IllegalArgumentException("Course not found"));
        Enrollment enrollment = enrollmentRepository.findFirstByUserAndCourse(user, course)
                .stream()
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Enrollment not found"));

        return enrollment.getProgress();
    }

    @Transactional
    public void updateProgress(Integer userId, Integer courseId, Integer progress) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));
        Enrollment enrollment = enrollmentRepository.findFirstByUserAndCourse(user, course)
                .stream()
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Enrollment not found"));

        enrollment.setProgress(progress);
        enrollmentRepository.save(enrollment);
    }

    @Transactional
    public EnrollmentDTO createEnrollment(Enrollment enrollment) {
        logger.debug("Creating new enrollment with data: {}", enrollment);

        Integer userId = enrollment.getUserId();
        Integer courseId = enrollment.getCourseId();

        if (userId == null || courseId == null) {
            throw new IllegalArgumentException("User ID and Course ID are required");
        }

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Course course = courseRepository.findById(courseId)
            .orElseThrow(() -> new IllegalArgumentException("Course not found"));
        
        // Increment user's inprogress count
        user.setInprogress(user.getInprogress() + 1);
        userRepository.save(user);
        
        Enrollment newEnrollment = new Enrollment();
        newEnrollment.setUser(user);
        newEnrollment.setCourse(course);
        newEnrollment.setEnrollDate(LocalDate.now().toString());
        newEnrollment.setProgress(0);

        try {
            return enrollmentMapper.toDto(enrollmentRepository.save(newEnrollment));
        } catch (Exception e) {
            logger.error("Error creating enrollment: ", e);
            // Rollback inprogress count if enrollment fails
            user.setInprogress(user.getInprogress() - 1);
            userRepository.save(user);
            throw new RuntimeException("Failed to create enrollment", e);
        }
    }
}