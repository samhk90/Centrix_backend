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

    @Autowired
    private ResultsRepository resultsRepository;

    @Autowired
    private AssessmentRepository assessmentRepository;


    @Transactional(readOnly = true)
    public List<CourseDTO> getCourses(Integer userId) {
        List<Course> courses = courseRepository.findAll();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        courses.forEach(course -> {
            if (course.getChapter() != null) {
                course.getChapter().getName();
            }
            if (course.getTrainer() != null) {
                course.getTrainer().getTfirstName();
            }
        });

        List<Course> filteredCourses = courses.stream()
                .filter(course ->
                        // Filter enrolled courses
                        !enrollmentRepository.existsByUserAndCourse(user, course)
                                // Filter courses by chapter
                                && course.getChapter() != null
                                && course.getChapter().getId().equals(user.getChapter().getId()))
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

    @Transactional(readOnly = true)
    public List<CourseDTO> getCompletedCourses(Integer userId) {
        List<Enrollment> enrollments = enrollmentRepository.findByUser_Uid(userId);
        
        List<Course> completedCourses = enrollments.stream()
                .filter(enrollment -> {
                    // Check if progress is 100%
                    if (enrollment.getProgress() != 100) {
                        return false;
                    }
                    
                    // Get assessment for this course
                    List<Assessment> assessments = assessmentRepository.findByCourse_CourseId(enrollment.getCourseId());
                    if (assessments.isEmpty()) {
                        return false;
                    }
                    
                    // Check if user has passed the assessment
                    for (Assessment assessment : assessments) {
                        results result = resultsRepository.findByUserIdAndAssessment_AssessmentId(userId, assessment.getAssessmentId());
                        if (result == null || !"PASS".equals(result.getStatus())) {
                            return false;
                        }
                    }
                    
                    return true;
                })
                .map(Enrollment::getCourse)
                .collect(Collectors.toList());

        return courseMapper.toCourseDtoList(completedCourses);
    }
}
