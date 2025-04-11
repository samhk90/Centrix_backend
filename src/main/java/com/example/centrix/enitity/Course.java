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
    @Column(name = "course_id")
    private Integer courseId;

    private String title;

    private String description;

    private String duration;

    @ManyToOne
    @JoinColumn(name = "cid", referencedColumnName = "id")
    private Chapter chapter;

    @ManyToOne
    @JoinColumn(name = "tid", referencedColumnName = "tid")
    private Trainer trainer;
}
