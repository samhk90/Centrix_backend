package com.example.centrix.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "questions")
@Getter
@Setter
public class Questions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "que_id")
    private Integer queId;
    private String question;
    private Integer marks;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String option5;
    private String option6;
    private String correctiOption;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "assessment_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Assessment assessment;
}
