package com.example.centrix.contollers;


import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class coursesList {
    private List<String> courses = new ArrayList<>(Arrays.asList("Java Basics", "Spring Boot"));

    // GET: Get all courses
    @GetMapping("/courses")
    public List<String> getCourses() {
        return courses;
    }


    @PostMapping("/courses")
    public String addCourse(@RequestBody String course) {
        courses.add(course);
        return "Course added: " + course;
    }

    // DELETE: Remove a course
    @DeleteMapping("/courses/{courseName}")
    public String deleteCourse(@PathVariable String courseName) {
        courses.remove(courseName);
        return "Course removed: " + courseName;
    }
}
