package com.alura.hotel.services;

import com.alura.hotel.dto.HotelDto;
import com.alura.hotel.services.request.RequestResultList;
import com.alura.hotel.services.request.RequestResultSingle;

public interface HotelService {

    void findAll(RequestResultList<HotelDto> result);
}
