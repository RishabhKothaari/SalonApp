package com.salon.SalonApp.services;

import com.salon.SalonApp.models.SalonServiceDetail;
import com.salon.SalonApp.models.Slot;
import com.salon.SalonApp.models.SlotStatus;
import com.salon.SalonApp.repositories.SalonSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;


@Service
public class SalonSlotServiceImpl implements SalonSlotService{

    @Autowired
    private SalonSlotRepository salonSlotRepository;

    @Autowired
    private SalonAvailableServicesImpl salonAvailableServices;

    @Override
    public Slot availableSlot(LocalDateTime date) {
        return salonSlotRepository.findAll().stream().filter(slot -> slot.getSlotFor().equals(date)).findFirst().get();
    }


    public List<Slot> findSlots(Long salonServiceId,String date){
//        System.out.println("impl date = "+date);
        SalonServiceDetail salonServiceDetail =  salonAvailableServices.getSalonServiceDetail(salonServiceId).orElseThrow(()->new RuntimeException("Invalid Service"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(date,formatter);
        LocalDateTime startDate = localDate.atTime(0,1);
        LocalDateTime endDate = localDate.atTime(23,59);
//        return salonSlotRepository.findByDate(today,tomorrow,salonServiceDetail.getId(), SlotStatus.AVAILABLE.ordinal());
        List<Slot>  availableSlots = salonSlotRepository.findAllBySlotForGreaterThanEqualAndSlotForLessThanEqualAndAvailableServicesContainingAndStatus(startDate,endDate,salonServiceDetail,SlotStatus.AVAILABLE);
        availableSlots.forEach(slot -> slot.setSelectedService(salonServiceDetail));
        return availableSlots;
    }

    public Slot findLockedSlotId(Long slotId) {

        return salonSlotRepository.findByIdAndStatus(slotId,SlotStatus.LOCKED);

    }

    public Optional<Slot> findAvailableSlotId(Long slotId) {

        return Optional.ofNullable(salonSlotRepository.findByIdAndStatus(slotId, SlotStatus.AVAILABLE));

    }

    public void setToLockedWithService(Slot slot, SalonServiceDetail serviceDetail) {
        slot.setStatus(SlotStatus.LOCKED);
        slot.setLockedAt(LocalDateTime.now());
        slot.setSelectedService(serviceDetail);
        save(slot);
    }

    public void setToConfirmed(Slot slot) {
        slot.setStatus(SlotStatus.CONFIRMED);
        slot.setConfirmedAt(LocalDateTime.now());
        save(slot);
    }

    @Transactional
    public void save(Slot slot) {
        salonSlotRepository.save(slot);
    }
}
