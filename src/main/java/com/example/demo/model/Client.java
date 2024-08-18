package com.example.demo.model;


import com.example.demo.model.dto.SubscriptionDTO;
import com.example.demo.model.dto.TrainingCardDTO;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder(toBuilder = true)
@Data
public class Client {
    private Long clientID;
    private String name;
    private String surname;
    private String bday;
    private String address;
    private String mobile;
    private String email;
    private Subscription subscriptionID;
    private TrainingCard trainingCardID;

}



