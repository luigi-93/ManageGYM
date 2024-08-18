package com.example.demo.controller;

import com.example.demo.mapper.ClientMapper;
import com.example.demo.mapper.TrainerMapper;
import com.example.demo.model.Client;
import com.example.demo.model.Trainer;
import com.example.demo.model.TrainingCard;
import com.example.demo.model.dto.ClientDTO;
import com.example.demo.model.dto.TrainerDTO;
import com.example.demo.service.ClientService;
import com.example.demo.service.TrainingCardService;
import com.example.demo.service.TrainerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/trainingCard")
public class TrainingCardController {

    @Autowired
    private TrainingCardService trainingCardService;
    @Autowired
    private ClientService clientService;

    @Autowired
    private TrainerService trainerService;


    @GetMapping
    //[GET] http://localhost:8080/trainingCard
    public List<TrainingCard> getAllTrainingCard(){
        return  trainingCardService.getAllTrainingCard();
    }

    //http://localhost:8080/trainingCard/updateTrainingCard
    @PutMapping("/updateTrainingCard")
    public TrainingCard updateTrainingCard(@Valid @RequestBody TrainingCard trainingCard) throws ParseException {
        return trainingCardService.updateTrainingCard(trainingCard);
    }


    @GetMapping ("/{id}")
    //il risultato sarà [GET] http://localhost:8080/trainingCard/1
    public TrainingCard getTrainingCardById(@PathVariable long id) {
        return trainingCardService.getTrainingCardById(id);
    }

    //[POST] http://localhost:8080/trainingCard
    @PostMapping
    public List<TrainingCard> addTrainingCard(@Valid @RequestBody List<TrainingCard> trainingCard) {
        return trainingCardService.addTrainingCard(trainingCard);
    }

    //[DELETE] http://localhost:8080/trainingCard
    @DeleteMapping("/{id}")
    public void deleteTrainingCard(@PathVariable long id) {
        trainingCardService.deleteTrainingCardById(id);

        System.out.println("The record has been deleted");
    }

    //[PUT] http://localhost:8080/trainingCard/addClient?trainingCard=1&client=1

    @PutMapping("/addClient")
    public ResponseEntity<TrainingCard> addClientToTrainingCard (@RequestParam long trainingCard, @RequestParam long client) throws ParseException {
        TrainingCard trainingCardById = trainingCardService.getTrainingCardById(trainingCard);
        Client clientById = clientService.getClientById(client);

        if (trainingCardById == null && clientById == null){
            return ResponseEntity.notFound().build();
        }


        trainingCardById.setClientID(clientById);
        trainingCardService.updateTrainingCard(trainingCardById);
        clientService.updateClient(clientById);

        return ResponseEntity.ok(trainingCardById);

    }


    //[PUT] http://localhost:8080/trainingCard/addTrainer?trainingCard=1&trainer=1
    @PutMapping("/addTrainer")
    public ResponseEntity<TrainingCard> addTrainerToTrainingCard (@RequestParam long trainingCard, @RequestParam long trainer) throws ParseException {
        TrainingCard trainingCardById = trainingCardService.getTrainingCardById(trainingCard);
        Trainer trainerById = trainerService.getTrainerById(trainer);

        if (trainingCardById == null && trainerById == null){
            return ResponseEntity.notFound().build();
        }


        trainingCardById.setTrainerID(trainerById);
        trainingCardService.updateTrainingCard(trainingCardById);
        trainerService.updateTrainer(trainerById);

        return ResponseEntity.ok(trainingCardById);

    }


}
