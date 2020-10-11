package com.salon.SalonApp.exception;

public class SalonException extends RuntimeException {
    private String message;

    public SalonException(String ex){
        this.message = ex;
    }
}
