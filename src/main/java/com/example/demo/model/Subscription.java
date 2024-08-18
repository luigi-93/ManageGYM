package com.example.demo.model;

import com.example.demo.model.dto.ClientDTO;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder(toBuilder = true)
@Data
public class Subscription {
    private Long subscriptionID;
    private Client clientID;
    private String type;
    private String startDate;
    private String endDate;
    private int price;

}
