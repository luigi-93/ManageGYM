package com.example.demo.mapper;

import com.example.demo.model.Subscription;
import com.example.demo.model.dto.ClientDTO;
import com.example.demo.model.dto.SubscriptionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {ClientMapper.class})
public interface SubscriptionMapper {
    SubscriptionMapper INSTANCE = Mappers.getMapper(SubscriptionMapper.class);




    @Mappings({
            @Mapping(target = "clientID", source = "clientID", ignore = true),
            @Mapping(target = "startDate", dateFormat = "dd-MM-yyyy"),
            @Mapping(target = "endDate", dateFormat = "dd-MM-yyyy")
    })
    SubscriptionDTO subscriptionToSubscriptionDTO(Subscription subscription);

    @Mappings({
            @Mapping(source = "subscriptionID", target = "subscriptionID"),
            @Mapping(source = "clientID.clientID", target = "clientID"),
            @Mapping(source = "type", target = "type"),
            @Mapping(target = "startDate", dateFormat = "dd-MM-yyyy"),
            @Mapping(target = "endDate", dateFormat = "dd-MM-yyyy"),
            @Mapping(source = "price", target = "price")
    })
    Subscription subscriptionDTOToSubscription(SubscriptionDTO subscriptionDTO);


//    @Named("fromClienteDTOToClienteId")
//    default Long fromClienteDTOToClienteId(ClientDTO clienteDTO){
//        if (clienteDTO != null){
//            return clienteDTO.stream().map(ClienteDTO::getIdCliente).collect(Collectors.toSet());
//        }
//        return new HashSet<>();
//
//    }
//
//    @Mapping(source = "clients", target = "clients", qualifiedByName = "fromClienteDTOToClienteId")
//    Abbonamento abbonamentoDTOToAbbonamento(AbbonamentoDTO abbonamentoDTO);




}
