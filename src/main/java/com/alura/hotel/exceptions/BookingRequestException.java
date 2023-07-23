package com.alura.hotel.exceptions;

public class BookingRequestException extends RuntimeException {

    public BookingRequestException(String message) {
        super(message);
    }
}
