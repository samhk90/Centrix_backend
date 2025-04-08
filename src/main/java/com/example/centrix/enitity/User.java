package com.example.centrix.enitity;

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

    @Column(length = 20)
    private String password;

     @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role", referencedColumnName = "id")
    private Role role;

    @ManyToOne
    @JoinColumn(name = "cid", referencedColumnName = "id")
    private Chapter chapter;

    // ✅ Getters and Setters
    public Integer getUid() { return uid; }
    public void setUid(Integer uid) { this.uid = uid; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmployeeid() { return employeeid; }
    public void setEmployeeid(String employeeid) { this.employeeid = employeeid; }



}
