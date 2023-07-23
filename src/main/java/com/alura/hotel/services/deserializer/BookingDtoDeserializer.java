package com.alura.hotel.services.deserializer;

import com.alura.hotel.dto.BookingDto;
import com.alura.hotel.dto.HotelDto;
import com.alura.hotel.dto.RoomDto;
import com.google.gson.*;

import java.lang.reflect.Type;

public class BookingDtoDeserializer implements JsonDeserializer<BookingDto> {

    @Override
    public BookingDto deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        Long id = jsonObject.get("id").getAsLong();
        String checkIn = jsonObject.get("checkIn").getAsString();
        String checkOut = jsonObject.get("checkOut").getAsString();
        Integer guests = jsonObject.get("guests").getAsInt();
        String bookingStatus = jsonObject.get("status").getAsString();
        RoomDto room = context.deserialize(jsonObject.get("room"), RoomDto.class);
        HotelDto hotel = context.deserialize(jsonObject.get("hotel"), HotelDto.class);
        Long userId = jsonObject.get("userId").getAsLong();


        BookingDto bookingDto = new BookingDto();
        bookingDto.setId(id);
        bookingDto.setCheckIn(checkIn);
        bookingDto.setCheckOut(checkOut);
        bookingDto.setGuests(guests);
        bookingDto.setStatus(bookingStatus);
        bookingDto.setRoomDto(room);
        bookingDto.setHotelDto(hotel);
        bookingDto.setUserId(userId);

        return bookingDto;
    }

}