package com.alura.hotel.enums;

public enum BookingStatusType {

    ACTIVE("ACTIVE"),
    CANCELLED("CANCELLED"),
    PENDING("PENDING"),
    CONFIRMED("CONFIRMED"),
    MISSED("MISSED");


    private final String type;


    BookingStatusType(String type) {
        this.type = type;
    }

    private String getType() {
        return this.type;
    }
}
