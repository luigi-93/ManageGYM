package com.example.demo.service;

import com.example.demo.mapper.TrainingCardMapper;
import com.example.demo.model.TrainingCard;
import com.example.demo.model.dto.TrainingCardDTO;
import com.example.demo.repository.TrainingCardRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TrainingCardService {

    @Autowired
    private TrainingCardRepository trainingCardRepository;

    @Transactional
    public List<TrainingCard> addTrainingCard(List<TrainingCard> trainingCard) {
        List<TrainingCardDTO> trainingCardDTO = trainingCard.stream().map(TrainingCardMapper.INSTANCE::trainingCardToTrainingCardDTO)
                .collect(Collectors.toList());

        List<TrainingCardDTO> saved = trainingCardRepository.saveAll(trainingCardDTO);

        return saved.stream().map(TrainingCardMapper.INSTANCE::trainingCardDTOToTrainingCard).collect(Collectors.toList());
    }

    @Transactional
    public List<TrainingCard> getAllTrainingCard () {
        return trainingCardRepository.findAll().stream().map(TrainingCardMapper.INSTANCE::trainingCardDTOToTrainingCard).toList();
    }

    @Transactional
    public TrainingCard updateTrainingCard(TrainingCard trainingCard) throws ParseException {
        TrainingCard preTrainingCard = trainingCardRepository.findById(trainingCard.getCardID())
                .map(TrainingCardMapper.INSTANCE::trainingCardDTOToTrainingCard)
                .orElseThrow(() -> new  EntityNotFoundException("Training Card not found"));

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

        TrainingCard updateTrainingCard = TrainingCard.builder()
                .cardID(preTrainingCard.getCardID())
                .clientID(trainingCard.getClientID() != null ? trainingCard.getClientID() : preTrainingCard.getClientID())
                .trainerID(trainingCard.getTrainerID() != null ? trainingCard.getTrainerID() : preTrainingCard.getTrainerID())
                .startDate(trainingCard.getStartDate() != null && !trainingCard.getStartDate().isEmpty() ? trainingCard.getStartDate(): preTrainingCard.getStartDate())
                .endDate(trainingCard.getEndDate() != null && !trainingCard.getEndDate().isEmpty() ? trainingCard.getEndDate() : preTrainingCard.getEndDate())
                .build();


        TrainingCardDTO upadateTrainingCardDTO = TrainingCardMapper.INSTANCE.trainingCardToTrainingCardDTO(updateTrainingCard);
        TrainingCardDTO savedTrainingCard = trainingCardRepository.save(upadateTrainingCardDTO);

        return TrainingCardMapper.INSTANCE.trainingCardDTOToTrainingCard(savedTrainingCard);

    }

    @Transactional
    public TrainingCard getTrainingCardById (long id) {
        return trainingCardRepository.findById(id).map(TrainingCardMapper.INSTANCE::trainingCardDTOToTrainingCard).orElse(null);

    }

    @Transactional
    public void deleteTrainingCardById (long id){
        trainingCardRepository.deleteById(id);
    }

}
