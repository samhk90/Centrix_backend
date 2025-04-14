package com.example.centrix.service;

import com.example.centrix.entity.Course;
import com.example.centrix.entity.Section;
import com.example.centrix.entity.Sections;
import com.example.centrix.entity.Topic;
import com.example.centrix.entity.Topics;
import com.example.centrix.repository.CourseRepository;
import com.example.centrix.repository.SectionRepository;
import com.example.centrix.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private TopicRepository topicRepository;

    public List<Course> getCourses() {
        return courseRepository.findAll();
    }

    public List<Course> getCoursesByCourseId(Integer courseId) {
        return courseRepository.findBycourseId(courseId);
    }
    public List<Course> getCoursesWithSectionsAndTopics() {
        List<Course> courses = courseRepository.findAll();
        for (Course course : courses) {
            List<Sections> sections = sectionRepository.findByCourse_CourseId(course.getCourseId());
            for (Sections section : sections) {
                List<Topics> topics = topicRepository.findBySection_SectionId(section.getSectionId());
                section.setTopics(topics);
            }
            course.setSections(sections);
        }
        return courses;
    }

}
