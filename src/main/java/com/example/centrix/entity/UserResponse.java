package com.example.centrix.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user_response", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"userId", "que_id"})
})
@Getter
@Setter
public class UserResponse {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Integer urid;
    @Column(nullable = false)
    private Integer userId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "que_id", referencedColumnName = "que_id")
    private Questions question;
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