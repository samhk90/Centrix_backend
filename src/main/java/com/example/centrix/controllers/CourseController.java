package com.example.centrix.controllers;

import com.example.centrix.dto.CourseDTO;
import com.example.centrix.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping("/courses/{userId}")
    public List<CourseDTO> getCourses(@PathVariable Integer userId) {
        return courseService.getCourses(userId);
    }

    @PostMapping("/enrolledcourse/{courseId}")
    public List<CourseDTO> getCoursesWithSTA(@PathVariable Integer courseId) {
        boolean flag = true;
        return courseService.getCoursesWithSectionsAndTopics(courseId, flag);
    }

    @PostMapping("/course/{courseId}")
    public List<CourseDTO> getCourseWithSectionandTopics(@PathVariable Integer courseId) {
        boolean flag = false;
        return courseService.getCoursesWithSectionsAndTopics(courseId, flag);
    }
}
