package com.example.demo.mapper;

import com.example.demo.model.TrainingCard;
import com.example.demo.model.dto.TrainingCardDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TrainingCardMapper {
    TrainingCardMapper INSTANCE = Mappers.getMapper(TrainingCardMapper.class);


    @Mappings({
            @Mapping(target = "clientID", source = "clientID", ignore = true),
            @Mapping(source = "trainerID", target = "trainerID", ignore = true),
            @Mapping(target = "startDate", dateFormat = "dd-MM-yyyy"),
            @Mapping(target = "endDate", dateFormat = "dd-MM-yyyy")
    })
    TrainingCardDTO trainingCardToTrainingCardDTO(TrainingCard trainingCard);


    @Mappings({
            @Mapping(source = "cardID", target = "cardID"),
            @Mapping(source = "clientID.clientID", target = "clientID"),
            @Mapping(source = "trainerID.trainerID", target = "trainerID"),
            @Mapping(target = "startDate", dateFormat = "dd-MM-yyyy"),
            @Mapping(target = "endDate", dateFormat = "dd-MM-yyyy")
    })
    TrainingCard trainingCardDTOToTrainingCard(TrainingCardDTO trainingCardDTO);
}
