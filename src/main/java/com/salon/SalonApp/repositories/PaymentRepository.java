package com.salon.SalonApp.repositories;

import com.salon.SalonApp.models.Payment;
import com.salon.SalonApp.models.Slot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment,Long> {
    Payment findBySlotAndEmail(Slot slot, String email);
}
