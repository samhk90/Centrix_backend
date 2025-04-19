package com.example.centrix.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Table(name = "enrollment", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"course_id", "user_id"})
})
@Getter
@Setter
@ToString(exclude = {"course", "user"})
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer enrollId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "enroll_date")
    private String enrollDate;

    @Column(name = "progress")
    private Integer progress;

    @JsonProperty("courseId")
    public Integer getCourseId() {
        try {
            return course != null ? course.getCourseId() : null;
        } catch (Exception e) {
            return null;
        }
    }

    @JsonProperty("userId")
    public Integer getUserId() {
        try {
            return user != null ? user.getUid() : null;
        } catch (Exception e) {
            return null;
        }
    }

    public void setCourseId(Integer courseId) {
        if (this.course == null) {
            this.course = new Course();
        }
        if (courseId != null) {
            this.course.setCourseId(courseId);
        }
    }

    public void setUserId(Integer userId) {
        if (this.user == null) {
            this.user = new User();
        }
        if (userId != null) {
            this.user.setUid(userId);
        }
    }

    @PrePersist
    public void prePersist() {
        if (this.enrollDate == null) {
            this.enrollDate = LocalDate.now().toString();
        }
        if (this.progress == null) {
            this.progress = 0;
        }
    }
}