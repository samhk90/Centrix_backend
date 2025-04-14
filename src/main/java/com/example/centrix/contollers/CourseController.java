package com.example.centrix.contollers;


import com.example.centrix.entity.Course;
import com.example.centrix.entity.Trainer;
import com.example.centrix.entity.Section;
import com.example.centrix.entity.Topic;
import com.example.centrix.repository.TrainerRepository;
import com.example.centrix.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/courses/{courseId}/sections")
    public List<Section> getSectionsByCourseId(@PathVariable Integer courseId) {
        return courseService.getSectionsByCourseId(courseId);
    }

    @GetMapping("/sections/{sectionId}/topics")
    public List<Topic> getTopicsBySectionId(@PathVariable Integer sectionId) {
        return courseService.getTopicsBySectionId(sectionId);
    }

    @PostMapping("/sections")
    public Section addSection(@RequestBody Section section) {
        return courseService.addSection(section);
    }

    @PostMapping("/topics")
    public Topic addTopic(@RequestBody Topic topic) {
        return courseService.addTopic(topic);
    }
}
