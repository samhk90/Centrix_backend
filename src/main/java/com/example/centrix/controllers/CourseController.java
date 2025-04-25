package com.example.centrix.controllers;


import com.example.centrix.entity.Course;
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
    public List<Course> getCourses(@PathVariable Integer userId) {
        List <Course> courses = courseService.getCourses(userId);
        System.out.println(courses);
        return courses;
    }

    @PostMapping("/enrolledcourse/{courseId}")
    public List<Course> getCoursesWithSTA(@PathVariable Integer courseId) {
        boolean flag=true;
        List<Course> courses = courseService.getCoursesWithSectionsAndTopics(courseId,flag);
        System.out.println(courses);
        return courses;
    }
    @PostMapping("/course/{courseId}")
    public List<Course> getCourseWithSectionandTopics(@PathVariable Integer courseId) {
        boolean flag=false;
        return courseService.getCoursesWithSectionsAndTopics(courseId,flag);
    }

    }
