package com.example.centrix.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@Entity
@Table(name = "course")
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cid", referencedColumnName = "id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Chapter chapter;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tid", referencedColumnName = "tid")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Trainer trainer;

    @JsonManagedReference
    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Sections> sections;
}
