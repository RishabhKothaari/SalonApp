package com.salon.SalonApp.controllers;

import com.salon.SalonApp.models.SalonServiceDetail;
import com.salon.SalonApp.services.SalonAvailableServicesImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SalonAvailableServicesController {
    @Autowired
    private SalonAvailableServicesImpl salonAvailableServices;

    @GetMapping("/api/services/services")
    public List<SalonServiceDetail> retrieveAvailableSalonServices(){
        return salonAvailableServices.getAvailableServices();
    }
}
