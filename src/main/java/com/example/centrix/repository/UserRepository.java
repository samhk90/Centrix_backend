package com.example.centrix.repository;



import com.example.centrix.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    // You can also add custom queries later if needed
}
