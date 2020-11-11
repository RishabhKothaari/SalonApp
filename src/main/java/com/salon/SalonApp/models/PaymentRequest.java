package com.salon.SalonApp.models;

import lombok.Data;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class PaymentRequest {
    private Long selectedServiceId;
    private Long slotId;

    @NotNull
    @Size(min = 3)
    private String firstName;

    @NotNull
    @Size(min = 3)
    private String lastName;

    @NotNull
    @Email(message = "Please provide a valid email")
    private String email;

    @NotNull
    @Size(min = 10)
    private String phoneNumber;
}
