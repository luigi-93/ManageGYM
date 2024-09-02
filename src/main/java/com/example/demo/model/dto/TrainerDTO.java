package com.example.demo.model.dto;


import com.example.demo.model.TrainingCard;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;



@Entity
@Getter
@Setter
@ToString
@Table(name = "trainer")
public class TrainerDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trainer_id")
    private long trainerID;

    @Size(min = 2, max = 10, message = "The name insert is not valid, please insert a name with at least two charatter")
    @Column(length = 50, nullable = false)
    private String name;

    @Size(min = 2, max = 10, message = "The surname insert is not valid, please insert a name with at least two charatter")
    @Column(length = 50, nullable = false)
    private String surname;


    @Size(min = 2, max = 50, message = "The expertise insert is not valid, please insert a name with at least two charatter")
    @Column(length = 50, nullable = false)
    private String expertise;

    @Size(min = 10, max = 12, message = "The Mobile Number insert is not valid, please insert a name with at least two charatter")
    @Column(length = 50, nullable = false)
    private String mobile;

    @Email(message = "The email insert is not correct")
    @Column(length = 50, nullable = false)
    private String email;



}
