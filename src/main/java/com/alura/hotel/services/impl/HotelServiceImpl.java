package com.alura.hotel.services.impl;

import com.alura.hotel.api.ApiEndpoints;
import com.alura.hotel.dto.HotelDto;
import com.alura.hotel.exceptions.HotelRequestException;
import com.alura.hotel.services.HotelService;
import com.alura.hotel.services.deserializer.HotelDtoDeserializer;
import com.alura.hotel.services.deserializer.LocalDateDeserializer;
import com.alura.hotel.services.request.RequestResultList;
import com.alura.hotel.util.RestClientUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import javafx.concurrent.Task;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.List;

@Service
public class HotelServiceImpl implements HotelService {

    @Override
    public void findAll(RequestResultList<HotelDto> result) throws HotelRequestException {

        RestClientUtil client = new RestClientUtil();
        Task<String> getHotelsRequest = client.get(ApiEndpoints.HOTELS_URL);

        getHotelsRequest.setOnSucceeded(event -> {
            String response = getHotelsRequest.getValue();
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(HotelDto.class, new HotelDtoDeserializer())
                    .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
                    .create();
            Type hotelDtoType = new TypeToken<List<HotelDto>>() {
            }.getType();
            List<HotelDto> hotels = gson.fromJson(response, hotelDtoType);
            result.accept(hotels);
        });

        getHotelsRequest.setOnFailed(event -> {
            throw new HotelRequestException("Failed at getting hotels from service: " + event.getSource().getException().getMessage());
        });
    }
}
