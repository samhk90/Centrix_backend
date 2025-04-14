package com.example.centrix.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "topics")
@Getter
@Setter
public class Topics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "topic_id")
    private Integer topicId;

    private String topicTitle;

    private Integer noa;            // number of artifacts

    @ManyToOne
    @JoinColumn(name = "section_id", referencedColumnName = "section_id")
    private Sections sections;
}
