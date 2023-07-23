package com.alura.hotel.exceptions;

public class RoomRequestException extends RuntimeException {

    public RoomRequestException(String message) {
        super(message);
    }
}
