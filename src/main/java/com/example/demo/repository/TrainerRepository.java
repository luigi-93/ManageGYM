package com.example.demo.repository;

import com.example.demo.model.dto.ClientDTO;
import com.example.demo.model.dto.TrainerDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainerRepository extends JpaRepository<TrainerDTO, Long> {
}
