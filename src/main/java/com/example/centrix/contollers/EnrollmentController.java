package com.example.centrix.controller;

import com.example.centrix.entity.Enrollment;
import com.example.centrix.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
@CrossOrigin(origins = "*")
public class EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Enrollment>> getEnrollmentsByUserId(@PathVariable Integer userId) {
        try {
            List<Enrollment> enrollments = enrollmentService.getEnrollmentsByUserId(userId);
            if (enrollments.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(enrollments);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/enrollments")
    public ResponseEntity<Enrollment> createEnrollment(@RequestBody Enrollment enrollment) {
        try {
            Enrollment savedEnrollment = enrollmentService.createEnrollment(enrollment);
            return ResponseEntity.ok(savedEnrollment);
        } catch (IllegalArgumentException e) {
            System.out.println("IllegalArgumentException: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
