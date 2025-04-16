package com.example.centrix.contollers;


import com.example.centrix.entity.Course;
import com.example.centrix.entity.Trainer;
import com.example.centrix.entity.Sections;
import com.example.centrix.entity.Topics;
import com.example.centrix.repository.TrainerRepository;
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

    @GetMapping("/courses")
    public List<Course> getCourses() {
        List <Course> courses = courseService.getCourses();
        System.out.println(courses);
        return courses;
    }

    @PostMapping("/course/{courseId}")
    public List<Course> getCoursesByTrainerId(@PathVariable Integer courseId) {

        System.out.println(courseService.getCoursesWithSectionsAndTopics(courseId));
        return courseService.getCoursesWithSectionsAndTopics(courseId);
    }

    }
