package com.example.centrix.contollers;

import com.example.centrix.entity.Enrollment;
import com.example.centrix.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class EnrollmentController {
    
    @Autowired
    private EnrollmentService enrollmentService;

    @GetMapping("/enrollments/user/{userId}")
    public List<Enrollment> getEnrollmentsByUserId(@PathVariable Integer userId) {
        return enrollmentService.getEnrollmentsByUserId(userId);
    }

    @PostMapping("/enrollments")
    public ResponseEntity<Enrollment> createEnrollment(@RequestBody Enrollment enrollment) {
        
        Enrollment savedEnrollment = enrollmentService.createEnrollment(enrollment);
        return ResponseEntity.ok(savedEnrollment);
    }
}