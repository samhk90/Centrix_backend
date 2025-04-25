package com.example.centrix.service;

import com.example.centrix.entity.*;
import com.example.centrix.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<Course> getCourses(Integer userId) {
        List<Course> courses = courseRepository.findAll();
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        courses.forEach(course -> {
            if (course.getChapter() != null) {
                course.getChapter().getName();
            }
            if (course.getTrainer() != null) {
                course.getTrainer().getTfirstName();
            }
        });
        System.out.println("Number of courses found: " + courses.size());
        return courses.stream()
                .filter(course ->
                        // Filter unenrolled courses
                        !enrollmentRepository.existsByUserAndCourse(user, course)
                                // Filter courses by chapter
                                && course.getChapter() != null
                                && course.getChapter().getId().equals(user.getChapter().getId()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Course> getCoursesByCourseId(Integer courseId) {
        return courseRepository.findBycourseId(courseId);
    }

    @Transactional(readOnly = true)
    public List<Course> getCoursesWithSectionsAndTopics(Integer courseId, boolean includeArtifacts) {
        List<Course> courses = courseRepository.findBycourseId(courseId);
        if (courses.isEmpty()) {
            return courses;
        }
        
        for (Course course : courses) {
            List<Sections> sections = sectionRepository.findByCourse_CourseId(course.getCourseId());
            for (Sections section : sections) {
                List<Topics> topics = topicRepository.findBySections_SectionId(section.getSectionId());
                if (includeArtifacts) {
                    for (Topics topic : topics) {
                        List<Artifacts> artifacts = artifactsRepository.findByTopics_TopicId(topic.getTopicId());
                        topic.setArtifacts(artifacts);
                    } 
                } else {
                    // Explicitly set artifacts to null when not including them
                    topics.forEach(topic -> topic.setArtifacts(null));
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
