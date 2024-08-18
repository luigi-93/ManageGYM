package com.example.demo.mapper;

import com.example.demo.model.Client;
import com.example.demo.model.dto.ClientDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@Mapper
public interface ClientMapper {
    ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);


    //@Mapping(target = "subscriptionID", ignore = true)
    //@Mapping(target = "trainingCardID", ignore = true)
    @Mapping(target = "bday", dateFormat = "dd-MM-yyyy")
    ClientDTO clientToClientDTO(Client client);

    //@Mapping(target = "subscriptionID", ignore = true)
    //@Mapping(target = "trainingCardID", ignore = true)
    @Mapping(target = "bday", dateFormat = "dd-MM-yyyy")
    Client clientDTOToClient(ClientDTO clientDTO);
}
