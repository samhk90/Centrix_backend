package com.example.centrix.controllers;

import com.example.centrix.dto.EnrollmentDTO;
import com.example.centrix.entity.Enrollment;
import com.example.centrix.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;

    @GetMapping("/enrollments/user/{userId}")
    public ResponseEntity<List<EnrollmentDTO>> getEnrollmentsByUserId(@PathVariable Integer userId) {
        try {
            List<EnrollmentDTO> enrollments = enrollmentService.getEnrollmentsByUserId(userId);
            if (enrollments.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(enrollments);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/enrollments/{userId}/{courseId}")
    public ResponseEntity<Boolean> getEnrollmentsByUserIdAndCourseId(
            @PathVariable Integer userId,
            @PathVariable Integer courseId) {
        try {
            boolean isEnrolled = enrollmentService.isEnrolled(userId, courseId);
            return ResponseEntity.ok(isEnrolled);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/enrollments/progress/{userId}/{courseId}")
    public ResponseEntity<Integer> getProgress(
            @PathVariable Integer userId,
            @PathVariable Integer courseId) {
        try {
            Integer progress = enrollmentService.getProgress(userId, courseId);
            return ResponseEntity.ok(progress);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/enrollments/progress/{userId}/{courseId}")
    public ResponseEntity<Void> updateProgress(
            @PathVariable Integer userId,
            @PathVariable Integer courseId,
            @RequestBody Integer progress) {
        try {
            enrollmentService.updateProgress(userId, courseId, progress);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/enrollments")
    public ResponseEntity<EnrollmentDTO> createEnrollment(@RequestBody Enrollment enrollment) {
        try {
            EnrollmentDTO savedEnrollment = enrollmentService.createEnrollment(enrollment);
            return ResponseEntity.ok(savedEnrollment);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
