package com.example.centrix.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "enrollment")
@Getter
@Setter
public class Enrollment {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer enrollId;

        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "course_id")
        @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
        private Course course;

        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "user_id")
        @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
        private User user;

        @Column(name = "enroll_date")
        private String enrollDate;

        @Column(name = "progress")
        private Integer progress;
}


//Entrollment{
//    enrollId[pk],
//            courseId[fk],
//            userId[fk],
//            enrollDate,
//            completedA,
//            progress
//}