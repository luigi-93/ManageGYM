package com.example.demo.repository;

import com.example.demo.model.dto.ClientDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<ClientDTO, Long> {
}
