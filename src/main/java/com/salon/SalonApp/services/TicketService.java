package com.salon.SalonApp.services;

import com.salon.SalonApp.models.Payment;
import com.salon.SalonApp.models.Slot;
import com.salon.SalonApp.models.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface TicketService {
    public Ticket book(Payment payment);
    public Optional<Ticket> findById(Long id);
}
