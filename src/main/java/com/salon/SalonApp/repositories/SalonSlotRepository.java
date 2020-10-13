package com.salon.SalonApp.repositories;

import com.salon.SalonApp.models.Slot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SalonSlotRepository extends JpaRepository<Slot,Long> {

    //SELECT * FROM slot WHERE slot_for >= '2020-10-10'::date AND slot_for < ('2020-10-10'::date + '1 day'::interval) AND status = 0;
    //SELECT * FROM slot WHERE slot_for >= cast('2020-10-10' as date) AND slot_for < (cast('2020-10-10' as date) + cast('1 day' as interval)) AND status = 0;
    @Query("FROM Slot WHERE slot_for >= cast(?1 as date) AND slot_for < cast(?2 as date) AND status = 0")
    List<Slot> findByDate(LocalDate today, LocalDate tomorrow);
}
