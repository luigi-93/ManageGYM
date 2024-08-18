package com.example.demo.mapper;

import com.example.demo.model.Trainer;
import com.example.demo.model.dto.TrainerDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TrainerMapper {
    TrainerMapper INSTANCE = Mappers.getMapper(TrainerMapper.class);


    @Mapping(target = "trainingCardID", ignore = true)
    TrainerDTO trainerToTrainerDTO(Trainer trainer);


    @Mapping(target = "trainingCardID", ignore = true)
    Trainer trainerDTOToTrainer(TrainerDTO trainerDTO);
}
