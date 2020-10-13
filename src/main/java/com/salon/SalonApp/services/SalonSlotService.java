package com.salon.SalonApp.services;

import com.salon.SalonApp.models.Slot;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


public interface SalonSlotService {
    public Slot availableSlot(LocalDateTime date);
    List<Slot> findSlots(LocalDate date);
}
