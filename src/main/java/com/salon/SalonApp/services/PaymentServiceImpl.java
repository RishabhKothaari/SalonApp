package com.salon.SalonApp.services;

import com.salon.SalonApp.exception.SalonException;
import com.salon.SalonApp.models.*;
import com.salon.SalonApp.repositories.PaymentRepository;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.stripe.Stripe;


import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.time.LocalDateTime;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService{
    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    SalonAvailableServicesImpl salonServices;

    @Autowired
    TicketServiceImpl ticketService;

    @Autowired
    SalonSlotServiceImpl slotService;

    @Value("${Stripe.Secret}")
    private String stripeApiKey;

    @PostConstruct
    public void onBeansLoaded(){
        Stripe.apiKey = stripeApiKey;
    }


    @Override
    public void validatePayment(String paymentIntentId) {
        try{
            PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);
            if(!paymentIntent.getStatus().equalsIgnoreCase("succeeded")){
                throw new SalonException("Payment is not succeeded,Invalid Entry" );
            }
            log.info("payment intent for "+paymentIntentId+","+paymentIntent.toJson());
        }catch (StripeException e){
            e.printStackTrace();
            throw new SalonException(e.getMessage());
        }
    }

    @Override
    public Payment findById(Long id) {
        return paymentRepository.findById(id).orElseThrow(()->new SalonException("Invalid payment id provided"));
    }

    public PaymentIntent createPayment(Payment payment){
        PaymentIntentCreateParams params =
                PaymentIntentCreateParams.builder()
                        .setCurrency("usd")
                        .setDescription("for Slot " + payment.getSlot().getId())
                        .setReceiptEmail(payment.getEmail())
                        .setAmount((payment.getAsCents()))
                        .putMetadata("integration_check", "accept_a_payment")
                        .build();

        try{
            log.debug("creating payment for "+params.toString());
            PaymentIntent paymentIntent = PaymentIntent.create(params);
            log.debug("created payment for "+params.toString());
            return paymentIntent;
        }catch(StripeException e){
            e.printStackTrace();
            throw new SalonException(e.getMessage());
        }
    }

    private Payment hasExistingPayment(PaymentRequest paymentRequest){
         Slot slot = slotService.findLockedSlotId(paymentRequest.getSlotId());
        if(slot == null){
            return null;
        }
        return paymentRepository.findBySlotAndEmail(slot,paymentRequest.getEmail());
    }

    public Payment initiatePayment(@Valid PaymentRequest paymentRequest){
        log.debug("payment request initiate payment for = "+paymentRequest.toString());
        Payment paid = hasExistingPayment(paymentRequest);
        if(paid != null){
            return paid;
        }
        Slot slot = slotService.findAvailableSlotId(paymentRequest.getSlotId()).orElseThrow(()->new SalonException("Invalid slot ID selected"));
        SalonServiceDetail salonServiceDetail = salonServices.getSalonServiceDetail(paymentRequest.getSelectedServiceId()).orElseThrow(()->new SalonException("Invalid service selected"));
        Payment payment = asPayment(paymentRequest,slot,salonServiceDetail);
        PaymentIntent paymentIntent = createPayment(payment);
        payment.setClientSecret(paymentIntent.getClientSecret());
        payment.setIntentId(paymentIntent.getId());
        slotService.setToLockedWithService(slot,salonServiceDetail);
        paymentRepository.save(payment);
        return payment;
    }

    private Payment asPayment(PaymentRequest paymentRequest, Slot slot, SalonServiceDetail salonServiceDetail) {
        Payment payment = new Payment();
        payment.setAmount(salonServiceDetail.getPrice());
        payment.setFirstName(paymentRequest.getFirstName());
        payment.setLastName(paymentRequest.getLastName());
        payment.setEmail(paymentRequest.getEmail());
        payment.setPhoneNumber(paymentRequest.getPhoneNumber());
        payment.setSlot(slot);
        payment.setCreated(LocalDateTime.now());
        payment.setSelectedService(salonServiceDetail);
        return payment;
    }

    @Transactional
    public Ticket confirm(Long paymentId){
        Payment payment = paymentRepository.findById(paymentId).orElseThrow(()->new SalonException("Invalid payment id"));
        validatePayment(payment.getIntentId());
        System.out.println("validated payment!!! for "+paymentId);
        slotService.setToConfirmed(payment.getSlot());
        payment.setStatus(PaymentStatus.SUCCESS);
        paymentRepository.save(payment);
        return ticketService.book(payment);
    }
}
