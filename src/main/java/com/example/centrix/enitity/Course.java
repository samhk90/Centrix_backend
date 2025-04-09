package com.example.centrix.enitity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.w3c.dom.Text;
@Entity
@Table(name = "course")
@Getter
@Setter
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer courseId;

    private String title;

    @Column(name = "description")
    private String description;

    private String duration;
    private String progress;

    @ManyToOne
    @JoinColumn(name = "tid", referencedColumnName = "tid")
    private Trainer trainer;
}