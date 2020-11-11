package com.salon.SalonApp.models;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
@Data
public class PaymentConfirmationResponse {
    private Ticket ticket;

    private SalonDetails salonDetails;
}
