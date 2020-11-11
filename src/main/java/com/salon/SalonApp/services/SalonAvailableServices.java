package com.salon.SalonApp.services;

import com.salon.SalonApp.models.SalonServiceDetail;

import java.util.List;
import java.util.Optional;

public interface SalonAvailableServices {
    public List<SalonServiceDetail> getAvailableServices();
    public Optional<SalonServiceDetail> getSalonServiceDetail(Long id);
}
