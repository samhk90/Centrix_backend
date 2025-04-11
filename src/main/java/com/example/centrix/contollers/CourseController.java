package com.example.centrix.contollers;


import com.example.centrix.enitity.Course;
import com.example.centrix.enitity.Trainer;
import com.example.centrix.repository.CourseRepository;
import com.example.centrix.repository.TrainerRepository;
import com.example.centrix.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class CourseController {

    @Autowired
    private TrainerRepository trainerRepository;
    @Autowired
    private  CourseService courseService;





    @GetMapping("/courses")
    public List<Course> getCourses() {
        return courseService.getCourses();
    }

    @PostMapping("/courses/{courseId}")
    public  List<Course> getCoursesByTrainerId(@PathVariable Integer courseId) {

        return courseService.getCoursesByCourseId(courseId);
    }


    @GetMapping("/trainers")
    public List<Trainer> getAllTrainers() {
        return trainerRepository.findAll();
    }
}