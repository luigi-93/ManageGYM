package com.example.demo.model;

import com.example.demo.model.dto.ClientDTO;
import com.example.demo.model.dto.TrainerDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TrainingCard {

    private Long cardID;
    private Client clientID;
    private Trainer trainerID;
    private String startDate;
    private String endDate;


}


