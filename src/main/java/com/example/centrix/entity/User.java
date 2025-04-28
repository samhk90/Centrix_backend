package com.example.centrix.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "user")
@Getter
@Setter
public class User {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer uid;

    @Column(length = 50)
    private String firstName;
    @Column(length = 50)
    private String lastName;
    @Column(length = 5, unique = true)
    private String employeeid;
    @Column(length = 50, unique = true)
    private String email;
    @Column(length = 20)
    private String password;
    private int flag;

     @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role", referencedColumnName = "id")
    private Role role;

    @ManyToOne
    @JoinColumn(name = "cid", referencedColumnName = "id")
    private Chapter chapter;


}
