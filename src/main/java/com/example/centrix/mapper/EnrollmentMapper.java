package com.example.centrix.mapper;

import com.example.centrix.dto.*;
import com.example.centrix.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class EnrollmentMapper {
    
    @Autowired
    private CourseMapper courseMapper;
    
    @Autowired
    private UserMapper userMapper;

    public EnrollmentDTO toDto(Enrollment enrollment) {
        if (enrollment == null) return null;

        EnrollmentDTO dto = new EnrollmentDTO();
        dto.setEnrollId(enrollment.getEnrollId());
        dto.setCourseId(enrollment.getCourseId());
        dto.setUserId(enrollment.getUserId());
        dto.setEnrollDate(enrollment.getEnrollDate());
        dto.setProgress(enrollment.getProgress());
        dto.setCourse(courseMapper.toDto(enrollment.getCourse()));
        dto.setUser(userMapper.toDto(enrollment.getUser()));
        return dto;
    }

    public List<EnrollmentDTO> toEnrollmentDtoList(List<Enrollment> enrollments) {
        if (enrollments == null) return null;
        return enrollments.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Enrollment toEntity(EnrollmentDTO dto) {
        if (dto == null) return null;

        Enrollment enrollment = new Enrollment();
        enrollment.setEnrollId(dto.getEnrollId());
        enrollment.setEnrollDate(dto.getEnrollDate());
        enrollment.setProgress(dto.getProgress());
        enrollment.setCourseId(dto.getCourseId());
        enrollment.setUserId(dto.getUserId());
        return enrollment;
    }

    public List<Enrollment> toEnrollmentList(List<EnrollmentDTO> dtos) {
        if (dtos == null) return null;
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}