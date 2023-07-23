package com.alura.hotel.services.request;

import com.alura.hotel.dto.BookingDto;

public class BookingRequestDtoMapper {
    private BookingDto booking;

    public static BookingRequestDtoMapper builder() {
        return new BookingRequestDtoMapper();
    }

    public BookingRequestDtoMapper setBooking(BookingDto booking) {
        this.booking = booking;
        return this;
    }

    public BookingRequest build() {
        if (booking == null) {
            throw new RuntimeException("BookingDTO entity in RequestBookingDtoMapper is null!");
        }

        return new BookingRequest(
                booking.getCheckIn(), booking.getCheckOut(), booking.getGuests(), booking.getStatus(),
                booking.getUserId(), booking.getHotelDto().getId(), booking.getRoomDto().getId()
        );
    }
}
