package com.example.centrix.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user_response")
@Getter
@Setter
public class UserResponse {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Integer urid;
    private Integer userId;

    private Integer quetionId;
    private String selectedoption;
    private Boolean isresponsecorrect;

}
//UserResponse{
//    urid[pk],
//            userId[fk],
//            quetionId[fk],
//            selectedoption,
//            isresponsecorrect
//}
