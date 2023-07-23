package com.alura.hotel.services;

import com.alura.hotel.dto.BookingDto;
import com.alura.hotel.services.request.RequestResultList;
import com.alura.hotel.services.request.RequestResultSingle;

public interface BookingService {

    void delete(Long id);
    void save(BookingDto bookingDto);

    void findAll(RequestResultList<BookingDto> result);

    void update(BookingDto bookingDto);
}
