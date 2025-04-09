package com.example.centrix.enitity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;



@Entity
@Table(name = "trainer")
@Getter
@Setter
public class Trainer {
    // ✅ Getters and Setters
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tid;

    @Setter
    @Column(length = 50)
    private String tfirstName;

    @Setter
    @Column(length = 50)
    private String tlastName;
    @Setter
    @Column(length = 50, unique = true)
    private String temail;
    @Setter
    @Column(length = 20)
    private String tpassword;
    @Setter
    @Column(length = 100)
    private String specialization;
}
