package com.example.centrix.service;

import com.example.centrix.dto.CourseDTO;
import com.example.centrix.entity.*;
import com.example.centrix.mapper.CourseMapper;
import com.example.centrix.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

    @Autowired
    private CourseMapper courseMapper;

    @Transactional(readOnly = true)
    public List<CourseDTO> getCourses(Integer userId) {
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

        List<Course> filteredCourses = courses.stream()
                .filter(course -> {
                    // Get enrollment for this course and user
                    List<Enrollment> enrollments = enrollmentRepository.findFirstByUserAndCourse(user, course);
                    
                    // If there's no enrollment or the enrollment progress is less than 100
                    boolean shouldInclude = !enrollments.isEmpty() ? 
                            enrollments.get(0).getProgress() < 100 : 
                            true;

                    return shouldInclude 
                            && course.getChapter() != null
                            && course.getChapter().getId().equals(user.getChapter().getId());
                })
                .collect(Collectors.toList());

        return courseMapper.toCourseDtoList(filteredCourses);
    }

    @Transactional(readOnly = true)
    public List<CourseDTO> getCoursesByCourseId(Integer courseId) {
        List<Course> courses = courseRepository.findBycourseId(courseId);
        return courseMapper.toCourseDtoList(courses);
    }

    @Transactional(readOnly = true)
    public List<CourseDTO> getCoursesWithSectionsAndTopics(Integer courseId, boolean includeArtifacts) {
        List<Course> courses = courseRepository.findBycourseId(courseId);
        if (courses.isEmpty()) {
            return courseMapper.toCourseDtoList(courses);
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
                    topics.forEach(topic -> topic.setArtifacts(null));
                }
                section.setTopics(topics);
            }
            course.setSections(sections);
        }
        return courseMapper.toCourseDtoList(courses);
    }
}
