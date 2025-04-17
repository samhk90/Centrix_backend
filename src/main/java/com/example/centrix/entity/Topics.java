package com.example.centrix.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@Table(name = "topics")
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Topics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "topic_id")
    private Integer topicId;

    private String topicTitle;

    private Integer noa;            // number of artifacts

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "section_id")
    private Sections sections;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToMany(mappedBy = "topics", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Artifacts> artifacts;

    public void setArtifacts(List<Artifacts> artifacts) {
        this.artifacts = artifacts;
    }
}
