package com.example.demo.model;

import com.example.demo.model.dto.TrainingCardDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Trainer {

    private long trainerID;
    private String name;
    private String surname;
    private String expertise;
    private String mobile;
    private String email;
    private TrainingCard trainingCardID;

}
