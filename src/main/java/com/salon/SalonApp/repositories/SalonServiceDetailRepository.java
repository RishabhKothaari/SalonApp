package com.salon.SalonApp.repositories;

import com.salon.SalonApp.models.SalonServiceDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalonServiceDetailRepository extends JpaRepository<SalonServiceDetail, Long> {
    public List<SalonServiceDetail> findAll();
}
