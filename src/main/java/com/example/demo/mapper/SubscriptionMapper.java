package com.example.demo.mapper;

import com.example.demo.model.Subscription;
import com.example.demo.model.dto.SubscriptionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {ClientMapper.class})
public interface SubscriptionMapper {
    SubscriptionMapper INSTANCE = Mappers.getMapper(SubscriptionMapper.class);

    @Mapping(target = "clientID", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "startDate", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "endDate", dateFormat = "dd-MM-yyyy")
    SubscriptionDTO subscriptionToSubscriptionDTO(Subscription subscription);

    @Mapping(target = "clientID", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "startDate", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "endDate", dateFormat = "dd-MM-yyyy")
    Subscription subscriptionDTOToSubscription(SubscriptionDTO subscriptionDTO);
}
