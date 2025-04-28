package com.example.centrix.repository;



import com.example.centrix.entity.UserResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface UserResponseRepository extends JpaRepository<UserResponse, Integer> {
    UserResponse findByUserIdAndQuestion_QueId(Integer userId, Integer quetionId);

    List<UserResponse> findByQuestion_Assessment_AssessmentId(Integer assessmentId);
}
