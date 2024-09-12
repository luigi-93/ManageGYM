package com.example.demo.model;


import com.example.demo.model.dto.SubscriptionDTO;
import com.example.demo.model.dto.TrainingCardDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Builder(toBuilder = false) // Keeps the builder without toBuilder method
@Data
@AllArgsConstructor // Generates a constructor with all fields
@NoArgsConstructor
public class Client {
    private Long clientID;
    private String name;
    private String surname;
    private String bday;
    private String address;
    private String mobile;
    private String email;





//
//    public void setBday(String bday) {
//        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
//        try {
//            Date parseDate = formatter.parse(bday);
//            this.bday = formatter.format(parseDate);
//        } catch (ParseException e) {
//            throw new IllegalArgumentException("Invalid date format, expected dd-MM-yyyy", e);
//        }
//    }

//    public Date getBday() {
//        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
//        try {
//            return formatter.parse(this.bday);
//        } catch (ParseException e) {
//            throw new IllegalArgumentException("Invalid date format in bday field", e);
//        }
//    }
}



