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

    @Setter
    @Getter
    @Column(length = 50)
    private String firstName;

    @Setter
    @Getter
    @Column(length = 50)
    private String lastName;

    @Setter
    @Getter
    @Column(length = 5, unique = true)
    private String employeeid;

    @Setter
    @Getter
    @Column(length = 50, unique = true)
    private String email;

    @Setter
    @Getter
    @Column(length = 20)
    private String password;

     @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role", referencedColumnName = "id")
    private Role role;

    @ManyToOne
    @JoinColumn(name = "cid", referencedColumnName = "id")
    private Chapter chapter;


}
