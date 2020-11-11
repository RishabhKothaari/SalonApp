package com.salon.SalonApp.controllers;

import com.salon.SalonApp.models.Ticket;
import com.salon.SalonApp.services.TicketServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class TicketController {
    @Autowired
    TicketServiceImpl ticketService;

    @GetMapping("/api/services/tickets/{ticketId}")
    public Ticket verifyTicket(@PathVariable Long ticketId){
        return ticketService.findById(ticketId).orElseThrow(()-> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid Ticket ID",null));
    }
}
