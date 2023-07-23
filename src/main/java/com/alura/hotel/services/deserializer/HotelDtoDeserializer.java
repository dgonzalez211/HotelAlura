package com.alura.hotel.services.deserializer;

import com.alura.hotel.dto.HotelDto;
import com.alura.hotel.dto.RoomDto;
import com.google.gson.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class HotelDtoDeserializer implements JsonDeserializer<HotelDto> {

    @Override
    public HotelDto deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        Long id = jsonObject.get("id").getAsLong();
        String name = jsonObject.get("name").getAsString();
        String email = jsonObject.get("email").getAsString();
        String phoneNumber = jsonObject.get("phoneNumber").getAsString();
        String telephoneNumber = jsonObject.get("telephoneNumber").getAsString();
        String address = jsonObject.get("address").getAsString();
        String location = jsonObject.get("location").getAsString();
        double rating = jsonObject.get("rating").getAsDouble();

        List<RoomDto> rooms = new ArrayList<>();
        JsonArray roomsArray = jsonObject.getAsJsonArray("rooms");
        for (JsonElement roomElement : roomsArray) {
            RoomDto room = context.deserialize(roomElement, RoomDto.class);
            rooms.add(room);
        }

        HotelDto hotelDto = new HotelDto();
        hotelDto.setId(id);
        hotelDto.setName(name);
        hotelDto.setEmail(email);
        hotelDto.setPhoneNumber(phoneNumber);
        hotelDto.setTelephoneNumber(telephoneNumber);
        hotelDto.setAddress(address);
        hotelDto.setLocation(location);
        hotelDto.setRating(rating);
        hotelDto.setRooms(rooms);

        return hotelDto;
    }
}