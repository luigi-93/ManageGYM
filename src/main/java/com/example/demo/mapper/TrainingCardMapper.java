package com.example.demo.mapper;

import com.example.demo.model.TrainingCard;
import com.example.demo.model.dto.TrainingCardDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TrainingCardMapper {
    TrainingCardMapper INSTANCE = Mappers.getMapper(TrainingCardMapper.class);


    @Mapping(target = "startDate", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "endDate", dateFormat = "dd-MM-yyyy")
    TrainingCardDTO trainingCardToTrainingCardDTO(TrainingCard trainingCard);


    @Mapping(target = "startDate", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "endDate", dateFormat = "dd-MM-yyyy")
    TrainingCard trainingCardDTOToTrainingCard(TrainingCardDTO trainingCardDTO);
}
