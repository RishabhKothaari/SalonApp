package com.salon.SalonApp.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
/*
name
address
city
state
zipcode
phone
 */
@Component
@Data

public class SalonDetails {
    @Value("${SalonDetails.name}")
    private String name;

    @Value("${SalonDetails.address}")
    private String address;

    @Value("${SalonDetails.city}")
    private String city;

    @Value("${SalonDetails.state}")
    private String state;

    @Value("${SalonDetails.zipcode}")
    private String zipcode;

    @Value("${SalonDetails.phone}")
    private String phone;

//    public SalonDetails getClonned(){
//        SalonDetails salonDetails = new SalonDetails();
//        salonDetails.setName(name);
//        salonDetails.setAddress(address);
//        salonDetails.setCity(city);
//        salonDetails.setState(state);
//        salonDetails.setPhone(phone);
//        salonDetails.setZipcode(zipcode);
//        return salonDetails;
//    }
}
