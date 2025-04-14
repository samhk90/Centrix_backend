package com.example.centrix.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "course")
@Getter
@Setter
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Integer courseId;

    private String title;

    private String description;

    private String duration;

    @Column(name="NOS")
    private Integer nos;

    @ManyToOne
    @JoinColumn(name = "cid", referencedColumnName = "id")
    private Chapter chapter;

    @ManyToOne
    @JoinColumn(name = "tid", referencedColumnName = "tid")
    private Trainer trainer;
}
