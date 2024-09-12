package com.example.demo.controller;

import com.example.demo.model.Trainer;
import com.example.demo.service.TrainerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/trainer")
public class TrainerController {

    @Autowired
    private TrainerService trainerService;


    @GetMapping
    public List<Trainer> getAllTrainer() {
        return trainerService.getAllTrainer();
    }


    @PutMapping("/updateTrainer")
    public Trainer updateTrainer(@Valid @RequestBody Trainer trainer) throws ParseException {
        return trainerService.updateTrainer(trainer);
    }

    @GetMapping("/{id}")
    public Trainer getTrainerById(@PathVariable long id) {
        return  trainerService.getTrainerById(id);
    }

    @PostMapping("/list")
    public  List<Trainer> addTrainers(@Valid @RequestBody List<Trainer> trainer){
        return trainerService.addTrainers(trainer);
    }


    @PostMapping
    public  Trainer addTrainer(@Valid @RequestBody Trainer trainer){
        return trainerService.addTrainer(trainer);
    }

    @DeleteMapping("/{id}")
    public  void deleteTrainer(@PathVariable long id) {
        trainerService.deleteTrainerById(id);

        System.out.println("The record has been deleted");
    }
}
