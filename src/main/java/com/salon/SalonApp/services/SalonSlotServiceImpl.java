package com.salon.SalonApp.services;

import com.salon.SalonApp.models.Slot;
import com.salon.SalonApp.repositories.SalonSlotRepository;
import jdk.vm.ci.meta.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Service
public class SalonSlotServiceImpl implements SalonSlotService{

    @Autowired
    private SalonSlotRepository salonSlotRepository;

    @Override
    public Slot availableSlot(LocalDateTime date) {
        return salonSlotRepository.findAll().stream().filter(slot -> slot.getSlotFor().equals(date)).findFirst().get();
    }


    public List<Slot> findSlots(LocalDate date){
//        System.out.println("impl date = "+date);
        LocalDate today = date;
        LocalDate tomorrow = date.plusDays(1);
        return salonSlotRepository.findByDate(today,tomorrow);
    }
}
