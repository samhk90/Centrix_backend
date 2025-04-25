package com.example.centrix.repository;



import com.example.centrix.entity.UserResponse;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserResponseRepository extends JpaRepository<UserResponse, Integer> {
    UserResponse findByUserIdAndQuetionId(Integer userId, Integer quetionId);
}
