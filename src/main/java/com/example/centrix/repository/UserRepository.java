package com.example.centrix.repository;



import com.example.centrix.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface UserRepository extends JpaRepository<User, Integer> {
            User findByEmail(String email);

    List<User> findByChapter_Id(Integer id);


    // You can also add custom queries later if needed
}
