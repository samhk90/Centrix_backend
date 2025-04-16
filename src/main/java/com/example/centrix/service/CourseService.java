package com.example.centrix.service;

import com.example.centrix.entity.Artifacts;
import com.example.centrix.entity.Course;
import com.example.centrix.entity.Sections;
import com.example.centrix.entity.Topics;
import com.example.centrix.repository.ArtifactsRepository;
import com.example.centrix.repository.CourseRepository;
import com.example.centrix.repository.SectionRepository;
import com.example.centrix.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private ArtifactsRepository artifactsRepository;

    @Transactional(readOnly = true)
    public List<Course> getCourses() {
        List<Course> courses = courseRepository.findAll();
        // Initialize the relationships to prevent lazy loading issues
        courses.forEach(course -> {
            if (course.getChapter() != null) {
                course.getChapter().getName();
            }
            if (course.getTrainer() != null) {
                course.getTrainer().getTfirstName();
            }
        });
        System.out.println("Number of courses found: " + courses.size());
        return courses;
    }

    @Transactional(readOnly = true)
    public List<Course> getCoursesByCourseId(Integer courseId) {
        return courseRepository.findBycourseId(courseId);
    }

    @Transactional(readOnly = true)
    public List<Course> getCoursesWithSectionsAndTopics(Integer courseId) {
        List<Course> courses = courseRepository.findBycourseId(courseId);
        if (courses.isEmpty()) {
            return courses;
        }
        
        for (Course course : courses) {
            List<Sections> sections = sectionRepository.findByCourse_CourseId(course.getCourseId());
            for (Sections section : sections) {
                List<Topics> topics = topicRepository.findBySections_SectionId(section.getSectionId());
                for(Topics topic : topics) {
                    List<Artifacts> artifacts = artifactsRepository.findByTopics_TopicId(topic.getTopicId());
                    topic.setArtifacts(artifacts);
                }
                section.setTopics(topics);
            }
            course.setSections(sections);
        }
        return courses;
    }

    @Transactional(readOnly = true)
    public List<Sections> getSectionsByCourseId(Integer courseId) {
        return sectionRepository.findByCourse_CourseId(courseId);
    }

    @Transactional(readOnly = true)
    public List<Topics> getTopicsBySectionId(Integer sectionId) {
        return topicRepository.findBySections_SectionId(sectionId);
    }
}
