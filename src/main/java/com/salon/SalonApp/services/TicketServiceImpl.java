package com.salon.SalonApp.services;

import com.salon.SalonApp.models.Payment;
import com.salon.SalonApp.models.SalonServiceDetail;
import com.salon.SalonApp.models.Ticket;
import com.salon.SalonApp.repositories.SalonServiceDetailRepository;
import com.salon.SalonApp.repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class TicketServiceImpl implements TicketService{

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private SalonServiceDetailRepository salonServiceDetail;

    @Override
    @Transactional
    public Ticket book(Payment payment) {
        Ticket ticket = new Ticket();
        ticket.setPayment(payment);
//        System.out.println("ticket being saved = "+ticket.toString());
        ticketRepository.save(ticket);
        return ticket;
    }

    @Override
    public Optional<Ticket> findById(Long id) {
        return ticketRepository.findById(id);
    }
}
