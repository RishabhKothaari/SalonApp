package com.salon.SalonApp.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@ToString
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    @ManyToOne(fetch = FetchType.EAGER)
    private SalonServiceDetail selectedService;

    @ManyToOne(fetch = FetchType.EAGER)
    private Slot slot;


    private Long amount;

    private LocalDateTime created;
    private LocalDateTime updated;
    private PaymentStatus status;

    private String clientSecret;
    private String intentId;



    private String firstName;


    private String email;


    private String lastName;


    private String phoneNumber;


    public String getName() {
        return firstName + " " + lastName;
    }
    public long getAsCents() {
        return amount * 100;
    }
}
