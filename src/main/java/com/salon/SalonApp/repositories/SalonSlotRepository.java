package com.salon.SalonApp.repositories;

import com.salon.SalonApp.models.SalonServiceDetail;
import com.salon.SalonApp.models.Slot;
import com.salon.SalonApp.models.SlotStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SalonSlotRepository extends JpaRepository<Slot,Long> {

    //SELECT * FROM slot WHERE slot_for >= '2020-10-10'::date AND slot_for < ('2020-10-10'::date + '1 day'::interval) AND status = 0;
    //SELECT * FROM slot WHERE slot_for >= cast('2020-10-10' as date) AND slot_for < (cast('2020-10-10' as date) + cast('1 day' as interval)) AND status = 0;
    @Query("FROM Slot WHERE slot_for >= cast(?2 as date) AND slot_for < cast(?3 as date) AND status = 0 AND id = ?1")
    List<Slot> findByDate(LocalDate today,LocalDate tomorrow,Long salonServiceDetailId, int slotStatus);

    List<Slot> findAllBySlotForGreaterThanEqualAndSlotForLessThanEqualAndAvailableServicesContainingAndStatus(LocalDateTime startTime, LocalDateTime endTime, SalonServiceDetail serviceDetail, SlotStatus status);

    Slot findByIdAndStatus(Long slotId, SlotStatus locked);
}
