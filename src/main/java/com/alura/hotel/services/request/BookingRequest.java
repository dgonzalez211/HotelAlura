package com.alura.hotel.services.request;

public class BookingRequest {

    private String checkIn;

    private String checkOut;

    private Integer guests;

    private String status;

    private Long userId;

    private Long hotelId;

    private Long roomId;

    public BookingRequest(String checkIn, String checkOut, Integer guests, String status, Long userId, Long hotelId, Long roomId) {
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.guests = guests;
        this.status = status;
        this.userId = userId;
        this.hotelId = hotelId;
        this.roomId = roomId;
    }

    public BookingRequest() {
    }

    public String getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }

    public Integer getGuests() {
        return guests;
    }

    public void setGuests(Integer guests) {
        this.guests = guests;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getHotelId() {
        return hotelId;
    }

    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }
}
