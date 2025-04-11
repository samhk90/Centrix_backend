package com.example.centrix.service;

import com.example.centrix.enitity.Course;
import com.example.centrix.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public  List<Course> getCourses() {
        return courseRepository.findAll();
    }

    public List<Course> getCoursesByCourseId(Integer courseId) {
        return courseRepository.findBycourseId(courseId);
    }
}