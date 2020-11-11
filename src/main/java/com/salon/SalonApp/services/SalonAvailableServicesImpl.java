package com.salon.SalonApp.services;

import com.salon.SalonApp.models.SalonServiceDetail;
import com.salon.SalonApp.repositories.SalonServiceDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SalonAvailableServicesImpl implements SalonAvailableServices {
    @Autowired
    private SalonServiceDetailRepository salonServiceDetailRepository;
    @Override
    public List<SalonServiceDetail> getAvailableServices() {
        return salonServiceDetailRepository.findAll();
    }
    @Override
    public Optional<SalonServiceDetail> getSalonServiceDetail(Long id){
        return salonServiceDetailRepository.findById(id);
    }
}
