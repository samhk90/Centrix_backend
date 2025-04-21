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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.time.LocalDate;
import java.util.Map;

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
    private CourseService courseService;

    @Transactional(readOnly = true)
    public List<Map<String, Object>> getEnrollmentsByUserId(Integer userId) {
        logger.debug("Fetching enrollments for user ID: {}", userId);
        List<Enrollment> enrollments = enrollmentRepository.findByUser_Uid(userId);
        List<Map<String, Object>> result = new ArrayList<>();

        enrollments.forEach(enrollment -> {
            Map<String, Object> enrollmentMap = new HashMap<>();
            enrollmentMap.put("enrollId", enrollment.getEnrollId());
            enrollmentMap.put("enrollDate", enrollment.getEnrollDate());
            enrollmentMap.put("progress", enrollment.getProgress());
            enrollmentMap.put("courseId", enrollment.getCourseId());
            enrollmentMap.put("userId", enrollment.getUserId());

            Course course = courseRepository.findById(enrollment.getCourseId())
                    .orElse(null);
            if (course != null) {
                Map<String, Object> courseMap = new HashMap<>();
                courseMap.put("courseId", course.getCourseId());
                courseMap.put("title", course.getTitle());
                courseMap.put("chapter", course.getChapter());
                courseMap.put("duration", course.getDuration());
                courseMap.put("nos", course.getNos());
                courseMap.put("trainer",course.getTrainer());
                enrollmentMap.put("course", courseMap);
            }

            result.add(enrollmentMap);
        });

        logger.debug("Found {} enriched enrollments for user ID: {}", result.size(), userId);
        return result;
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
    public Integer getProgress(Integer userId,Integer courseId){
        logger.debug("Fetching progress for user ID: {} in course ID: {}", userId, courseId);
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Course course = courseRepository.findById(courseId)
            .orElseThrow(() -> new IllegalArgumentException("Course not found"));
        Enrollment enrollment = enrollmentRepository.findByUserAndCourse(user, course)
                .stream()
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Enrollment not found"));

        return enrollment.getProgress();
    }

    @Transactional
    public  void updateProgress(Integer userId, Integer courseId, Integer progress) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));
        Enrollment enrollment = enrollmentRepository.findByUserAndCourse(user,course)
                .stream()
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Enrollment not found"));

            enrollment.setProgress(progress);
            enrollmentRepository.save(enrollment);
    }
    @Transactional
    public Enrollment createEnrollment(Enrollment enrollment) {
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