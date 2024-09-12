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
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.ErrorResponseException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.time.LocalDateTime.now;


@Service
@RequiredArgsConstructor
public class SubscriptionService {


    private final SubscriptionRepository subscriptionRepository;


    private final ClientRepository clientRepository;

    @Transactional
    public List<Subscription> addSubscriptions(List<Subscription> subscriptions){

        List<SubscriptionDTO> subscriptionDTO = subscriptions
                .stream()
                .map(sub -> {
                    if (sub.getClientID() == null)  {
                        throw new EntityNotFoundException("Please insert the client ");
                    }
                    ClientDTO clientDTO = clientRepository.findById(sub.getClientID()).orElseThrow();
                    SubscriptionDTO subDTO = SubscriptionMapper.INSTANCE.subscriptionToSubscriptionDTO(sub);
                    subDTO.setClientID(clientDTO);

                    return subscriptionRepository.save(subDTO);
                })
                .toList();


        return subscriptionDTO.stream().map(SubscriptionMapper.INSTANCE::subscriptionDTOToSubscription).toList();

    }

    public List<Subscription> getAllSubscriptionExpiring() {

        LocalDate today = LocalDate.now();
        LocalDate tenDate = today.plusDays(10);

        return subscriptionRepository
                .findAll()
                .stream()
                .filter(subscriptionDTO -> {
                    LocalDate exSub = subscriptionDTO.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    return exSub.isAfter(today) && exSub.isBefore(tenDate);
                })

                .map(SubscriptionMapper.INSTANCE::subscriptionDTOToSubscription)
                .toList();

    }
    public Subscription addSubscription(Subscription subscription) {

        if (subscription.getClientID() == null)  {
            throw new EntityNotFoundException("Please insert the client ");
        }

        ClientDTO clientDTO = clientRepository.findById(subscription.getClientID()).orElseThrow();
        SubscriptionDTO subscriptionDTO = SubscriptionMapper.INSTANCE.subscriptionToSubscriptionDTO(subscription);
        subscriptionDTO.setClientID(clientDTO);
        SubscriptionDTO saved = subscriptionRepository.save(subscriptionDTO);

        return SubscriptionMapper.INSTANCE.subscriptionDTOToSubscription(saved);
    }

    @Transactional
    public List<Subscription> getAllSubscription() {
        List<Subscription> subList = subscriptionRepository.findAll().stream().map(SubscriptionMapper.INSTANCE::subscriptionDTOToSubscription).toList();
        System.out.println(subList);
        return subList;
    }




    public List<Subscription> getAllSubscriptionAlreadyExpired() {

        LocalDate today = LocalDate.now();


        return subscriptionRepository
                .findAll()
                .stream()
                .filter(subscriptionDTO -> {
                    LocalDate exSub = subscriptionDTO.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    return exSub.isBefore(today);
                })

                .map(SubscriptionMapper.INSTANCE::subscriptionDTOToSubscription)
                .toList();
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
