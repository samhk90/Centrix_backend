package com.example.centrix.repository;

import com.example.centrix.enitity.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainerRepository extends JpaRepository<Trainer, Integer> {
    // You can also add custom queries later if needed
}