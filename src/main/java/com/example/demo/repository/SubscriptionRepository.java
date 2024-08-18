package com.example.demo.repository;

import com.example.demo.model.dto.ClientDTO;
import com.example.demo.model.dto.SubscriptionDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<SubscriptionDTO, Long> {
}
