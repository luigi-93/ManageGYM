package com.example.demo.repository;

import com.example.demo.model.dto.ClientDTO;
import com.example.demo.model.dto.SubscriptionDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepository extends JpaRepository<SubscriptionDTO, Long> {
}
