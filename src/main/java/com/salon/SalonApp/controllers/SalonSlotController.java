package com.salon.SalonApp.controllers;

import com.salon.SalonApp.models.Slot;
import com.salon.SalonApp.services.SalonSlotServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
public class SalonSlotController {
    @Autowired
    private SalonSlotServiceImpl salonSlotService;

    @GetMapping("/api/services/slots/{date}")
    public List<Slot> retrieveAvailableSlots(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date){
        return salonSlotService.findSlots(date);
    }
}
