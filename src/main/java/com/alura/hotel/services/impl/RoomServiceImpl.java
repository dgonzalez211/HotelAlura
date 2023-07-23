package com.alura.hotel.services.impl;

import com.alura.hotel.dto.RoomDto;
import com.alura.hotel.api.ApiEndpoints;
import com.alura.hotel.exceptions.RoomRequestException;
import com.alura.hotel.services.RoomService;
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
public class RoomServiceImpl implements RoomService {

    @Override
    public void findAll(RequestResultList<RoomDto> result) throws RoomRequestException {

        RestClientUtil client = new RestClientUtil();
        Task<String> getRoomsRequest = client.get(ApiEndpoints.ROOMS_URL);

        getRoomsRequest.setOnSucceeded(event -> {
            String response = getRoomsRequest.getValue();
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
                    .create();
            Type roomDtoType = new TypeToken<List<RoomDto>>() {}.getType();
            List<RoomDto> rooms = gson.fromJson(response, roomDtoType);
            result.accept(rooms);
        });

        getRoomsRequest.setOnFailed(event -> {
            throw new RoomRequestException("Failed at getting rooms: " + event.getSource().getException().getMessage());
        });
    }
}
