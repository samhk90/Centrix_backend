package com.example.centrix.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "sections")
@Getter
@Setter
public class Sections {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "section_id")
    private Integer sectionId;

    private String sectionTitle;

    @Column(name = "number_of_topics")
    private Integer numberOfTopics;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @JsonManagedReference
    @OneToMany(mappedBy = "sections", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Topics> topics;

    public void setTopics(List<Topics> topics) {
        this.topics = topics;
    }
}