package com.example.demo.model.dto;

import com.example.demo.model.Client;
import com.example.demo.model.Trainer;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import jdk.jfr.Name;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@Table(name = "trainingCard")
public class TrainingCardDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_id")
    private Long cardID;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private ClientDTO clientID;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "trainer_id")
    private TrainerDTO trainerID;

    @Column(length = 50, nullable = false)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    //@DateTimeFormat(fallbackPatterns = "dd-MM-yyyy")
    private Date startDate;

    @Column(length = 50, nullable = false)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    //@DateTimeFormat(fallbackPatterns = "dd-MM-yyyy")
    private Date endDate;






}
