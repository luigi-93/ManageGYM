package com.example.demo.service;


import com.example.demo.mapper.ClientMapper;
import com.example.demo.mapper.SubscriptionMapper;
import com.example.demo.model.Client;
import com.example.demo.model.Subscription;
import com.example.demo.model.dto.ClientDTO;
import com.example.demo.model.dto.SubscriptionDTO;
import com.example.demo.repository.ClientRepository;
import com.example.demo.repository.SubscriptionRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubscriptionService {
    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Transactional
    public List<Subscription> addSubscription(List<Subscription> subscriptions){
        List<SubscriptionDTO> subscriptionDTO = subscriptions
                .stream()
                .map(SubscriptionMapper.INSTANCE::subscriptionToSubscriptionDTO)
                .collect(Collectors.toList());

        List<SubscriptionDTO> saved = subscriptionRepository.saveAll(subscriptionDTO);

        return saved.stream().map(SubscriptionMapper.INSTANCE::subscriptionDTOToSubscription).collect(Collectors.toList());
    }

    @Transactional
    public List<Subscription> getAllSubscription() {
        return subscriptionRepository.findAll().stream().map(SubscriptionMapper.INSTANCE::subscriptionDTOToSubscription).toList();
    }


    @Transactional
    public Subscription updateSubscription(Subscription subscription) throws ParseException {
        Subscription preSub = subscriptionRepository.findById(subscription.getSubscriptionID()).map(SubscriptionMapper.INSTANCE::subscriptionDTOToSubscription).orElseThrow(() -> new EntityNotFoundException("Subscription not found"));

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

        Subscription updateSub = Subscription.builder()
                .subscriptionID(preSub.getSubscriptionID())
                .type(subscription.getType() != null && !subscription.getType().isEmpty() ? subscription.getType() : preSub.getType())
                .endDate(subscription.getEndDate() != null && !subscription.getEndDate().isEmpty() ? subscription.getEndDate() : preSub.getEndDate())
                .startDate(subscription.getStartDate() != null && !subscription.getStartDate().isEmpty() ? subscription.getStartDate() : preSub.getStartDate())
                .price(subscription.getPrice() != 0 ? subscription.getPrice() : preSub.getPrice())
                .clientID(subscription.getClientID() != null ? subscription.getClientID() : preSub.getClientID())
                .build();

        SubscriptionDTO updateSubDTO = SubscriptionMapper.INSTANCE.subscriptionToSubscriptionDTO(updateSub);
        SubscriptionDTO savedSub = subscriptionRepository.save(updateSubDTO);

        return SubscriptionMapper.INSTANCE.subscriptionDTOToSubscription(savedSub);

    }

    @Transactional
    public Subscription getSubscriptionById(long id) {
        return subscriptionRepository.findById(id)
                .map(SubscriptionMapper.INSTANCE::subscriptionDTOToSubscription).orElse(null);
    }

    @Transactional
    public void deleteSubscriptionById(long id) {
        subscriptionRepository.deleteById(id);
    }

}
