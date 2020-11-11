package com.salon.SalonApp.services;

import com.salon.SalonApp.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentService {
    void validatePayment(String paymentIntentId);
    Payment findById(Long id);
}
