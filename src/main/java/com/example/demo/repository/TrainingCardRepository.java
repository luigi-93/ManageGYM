package com.example.demo.repository;

import com.example.demo.model.dto.TrainingCardDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainingCardRepository extends JpaRepository<TrainingCardDTO, Long> {
}
