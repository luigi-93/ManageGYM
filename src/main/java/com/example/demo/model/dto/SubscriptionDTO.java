package com.example.demo.model.dto;

import com.example.demo.model.Client;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import jdk.jfr.Name;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@Entity
@Getter
@Setter
@ToString
@Table(name = "subscription")
public class SubscriptionDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subscription_id")
    private Long subscriptionID;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private ClientDTO clientID;

    @Size(min = 2, max = 10, message = "The type insert is not valid, please insert a name with at least two charatter")
    @Column(length = 50, nullable = false)
    private String type;

    @Column(length = 50, nullable = false)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    //@DateTimeFormat(fallbackPatterns = "dd-MM-yyyy")
    private Date startDate;

    @Column(length = 50, nullable = false)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date endDate;


    @Column(length = 50, nullable = false)
    @Min(value = 0, message = "Price can not be negative")
    private int price;








}
