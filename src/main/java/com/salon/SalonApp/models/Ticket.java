package com.salon.SalonApp.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@ToString
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    public Payment payment;

    TicketStatus ticketStatus = TicketStatus.BOOKED;
}
