package com.example.demo.model.dto;


import com.example.demo.model.Subscription;
import com.example.demo.model.TrainingCard;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


//la parte tra parentisi gestisce la modifica di oggetti già esistenti, credo che serva anche a me.


@Getter
@Setter
@ToString
@Entity
@Table(name = "client")
public class ClientDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id")
    private Long clientID;

    @Size(min = 2, max = 20, message = "The name insert is not valid, please insert a name with at least two character")
    @Column(length = 50, nullable = false)
    private String name;

    @Size(min = 2, max = 30, message = "The surname insert is not valid, please insert a surname with at least two character")
    @Column(length = 50, nullable = false)
    private String surname;


    @Column(length = 50, nullable = false)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date bday;

    @Size(min = 2, max = 50, message = "The Address insert is not valid, please insert a correct address")
    @Column(length = 50, nullable = false)
    private String address;

    @Size(min =10, max = 14, message = "The Mobile Number insert is not valid, please insert a correct mobile number")
    @Column(length = 50, nullable = false)
    private String mobile;

    @Email(message = "The email insert is not correct")
    @Column(length = 50, nullable = false)
    private String email;


}
