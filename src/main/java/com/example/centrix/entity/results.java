package com.example.centrix.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "results")
@Getter
@Setter
public class results {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer resultId;

    private Integer userId;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "assessment_id")
    private Assessment assessment;

    private Integer obtainedMarks;
    private String status;
    private Long timeTaken;
}