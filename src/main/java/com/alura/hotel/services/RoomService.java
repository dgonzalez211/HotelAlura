package com.alura.hotel.services;

import com.alura.hotel.dto.RoomDto;
import com.alura.hotel.services.request.RequestResultList;

public interface RoomService {

    void findAll(RequestResultList<RoomDto> result);
}
