package com.alura.hotel.exceptions;

public class HotelRequestException extends RuntimeException {

    public HotelRequestException(String message) {
        super(message);
    }
}
