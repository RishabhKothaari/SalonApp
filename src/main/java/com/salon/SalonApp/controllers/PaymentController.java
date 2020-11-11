package com.salon.SalonApp.controllers;

import com.salon.SalonApp.exception.SalonException;
import com.salon.SalonApp.models.*;
import com.salon.SalonApp.services.PaymentService;
import com.salon.SalonApp.services.PaymentServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ConstraintViolationException;

@RestController
@Slf4j
public class PaymentController {

    @Autowired
    PaymentServiceImpl paymentService;

    @Autowired
    PaymentConfirmationResponse paymentConfirmationResponse;

    @Autowired
    SalonDetails salonDetails;

    @PostMapping("/api/services/payments/initiate")
    public Payment initiatePayment(@RequestBody PaymentRequest paymentRequest){
        System.out.println("payment request = "+paymentRequest.toString());
        try{
            return paymentService.initiatePayment(paymentRequest);
        }catch (ConstraintViolationException e){
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage(),e);
        }catch (SalonException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage(),e);
        }
    }


    @GetMapping("/api/services/payments/{id}")
    public Payment verifyPayment(@PathVariable Long id){
        try{
            return paymentService.findById(id);
        }catch (ConstraintViolationException e){
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage(),e);
        }catch (SalonException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage(),e);
        }
    }


    @PutMapping("api/services/payments/confirm/{id}")
    public PaymentConfirmationResponse confirmSlot(@PathVariable Long id){
        try {
            Ticket ticket = paymentService.confirm(id);
            log.info("ticket saved = "+ticket.toString());
//            ticket.getPayment().getSlot().setAvailableServices(null);
            paymentConfirmationResponse.setTicket(ticket);
            paymentConfirmationResponse.setSalonDetails(salonDetails);
            log.info("payment response = "+paymentConfirmationResponse.toString());
            return paymentConfirmationResponse;
        }catch (SalonException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage(),e);
        }
    }
}
