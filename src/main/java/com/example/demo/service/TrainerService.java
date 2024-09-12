package com.example.demo.service;

import com.example.demo.mapper.ClientMapper;
import com.example.demo.mapper.TrainerMapper;
import com.example.demo.model.Client;
import com.example.demo.model.Trainer;
import com.example.demo.model.dto.ClientDTO;
import com.example.demo.model.dto.TrainerDTO;
import com.example.demo.repository.TrainerRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrainerService {


    private final TrainerRepository trainerRepository;

    @Transactional
    public List<Trainer> addTrainers(List<Trainer> trainer) {
        List<TrainerDTO> trainerDTO = trainer
                .stream()
                .map(TrainerMapper.INSTANCE::trainerToTrainerDTO)
                .collect(Collectors.toList());

        List<TrainerDTO> saved = trainerRepository.saveAll(trainerDTO);

        return saved.stream().map(TrainerMapper.INSTANCE::trainerDTOToTrainer).collect(Collectors.toList());
    }

    public Trainer addTrainer(Trainer trainer) {
        TrainerDTO trainerDTO = TrainerMapper.INSTANCE.trainerToTrainerDTO(trainer);
        TrainerDTO saved = trainerRepository.save(trainerDTO);
        return TrainerMapper.INSTANCE.trainerDTOToTrainer(saved);
    }


    @Transactional
    public List<Trainer> getAllTrainer() {
        return trainerRepository.findAll().stream().map(TrainerMapper.INSTANCE::trainerDTOToTrainer).toList();
    }

    @Transactional
    public Trainer updateTrainer (Trainer trainer) throws ParseException {
        Trainer preTrainer = trainerRepository.findById(trainer.getTrainerID()).map(TrainerMapper.INSTANCE::trainerDTOToTrainer).orElseThrow(()-> new EntityNotFoundException("Trainer not found"));

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

        Trainer updateTrainer = Trainer.builder()
                .trainerID(preTrainer.getTrainerID())
                .name(trainer.getName() != null && !trainer.getName().isEmpty() ? trainer.getName() : preTrainer.getName())
                .surname(trainer.getSurname() != null && !trainer.getSurname().isEmpty() ? trainer.getSurname() : preTrainer.getSurname())
                .expertise(trainer.getExpertise() != null && !trainer.getExpertise().isEmpty() ? trainer.getExpertise() : preTrainer.getExpertise()).mobile(trainer.getMobile() != null && !trainer.getMobile().isEmpty() ? trainer.getMobile() : preTrainer.getMobile())
                .email(trainer.getEmail() != null && !trainer.getEmail().isEmpty() ? trainer.getEmail() : preTrainer.getEmail())
                .build();

        TrainerDTO updateTrainerDTO = TrainerMapper.INSTANCE.trainerToTrainerDTO(updateTrainer);
        TrainerDTO savedTrainer = trainerRepository.save(updateTrainerDTO);

        return TrainerMapper.INSTANCE.trainerDTOToTrainer(savedTrainer);
    }

    @Transactional
    public Trainer getTrainerById(long id){
        return trainerRepository.findById(id).map(TrainerMapper.INSTANCE::trainerDTOToTrainer).orElse(null);
    }

    @Transactional
    public void deleteTrainerById(long id){
        trainerRepository.deleteById(id);
    }
}
