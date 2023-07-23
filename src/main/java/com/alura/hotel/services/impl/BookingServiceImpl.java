package com.alura.hotel.services.impl;

import com.alura.hotel.HotelApp;
import com.alura.hotel.api.ApiEndpoints;
import com.alura.hotel.dto.BookingDto;
import com.alura.hotel.enums.Scenes;
import com.alura.hotel.exceptions.BookingRequestException;
import com.alura.hotel.services.BookingService;
import com.alura.hotel.services.adapter.LocalDateAdapter;
import com.alura.hotel.services.deserializer.BookingDtoDeserializer;
import com.alura.hotel.services.request.BookingRequest;
import com.alura.hotel.services.request.BookingRequestDtoMapper;
import com.alura.hotel.services.request.RequestResultList;
import com.alura.hotel.session.SessionManagerFactory;
import com.alura.hotel.util.RestClientUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import javafx.application.Platform;
import javafx.concurrent.Task;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    @Override
    public void delete(Long id) {
        RestClientUtil client = new RestClientUtil();
        Task<String> bookingRequest = client.delete(ApiEndpoints.BOOKING_DELETE_URL + id);

        bookingRequest.setOnSucceeded(event -> HotelApp.getNavigator().navigate(Scenes.USER_BOOKINGS));

        bookingRequest.setOnFailed(event -> {
            throw new BookingRequestException("Booking deletion failed: " + event.getSource().getException().getMessage());
        });
    }

    @Override
    public void save(BookingDto bookingDto) throws BookingRequestException {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
        RestClientUtil client = new RestClientUtil();

        BookingRequest bookingRequestDto = BookingRequestDtoMapper.builder().setBooking(bookingDto).build();
        String bodyRequest = gson.toJson(bookingRequestDto);

        Task<String> bookingRequest = client.post(ApiEndpoints.BOOKINGS_URL, bodyRequest);

        bookingRequest.setOnFailed(event -> {
            throw new BookingRequestException("Booking creation failed: " + event.getSource().getException().getMessage());
        });
    }

    @Override
    public void findAll(RequestResultList<BookingDto> result) {

        RestClientUtil client = new RestClientUtil();

        String username = SessionManagerFactory.getInstance().getUsername();
        Task<String> getBookingsRequest = client.get(ApiEndpoints.USER_BOOKINGS_URL + username);

        getBookingsRequest.setOnSucceeded(event -> {
            String response = getBookingsRequest.getValue();
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(BookingDto.class, new BookingDtoDeserializer())
                    .create();
            Type bookingDtoType = new TypeToken<List<BookingDto>>() {
            }.getType();
            List<BookingDto> userBookings = gson.fromJson(response, bookingDtoType);
            result.accept(userBookings);
        });

        getBookingsRequest.setOnFailed(event -> {
            throw new RuntimeException("There was an error at bookings request: " + event.getSource().getException().getMessage());
        });
    }

    @Override
    public void update(BookingDto bookingDto) throws BookingRequestException {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
        BookingRequest bookingRequestDto = BookingRequestDtoMapper.builder().setBooking(bookingDto).build();
        String bodyRequest = gson.toJson(bookingRequestDto);

        RestClientUtil client = new RestClientUtil();
        Task<String> bookingRequest = client.put(ApiEndpoints.BOOKING_UPDATE_URL + bookingDto.getId(), bodyRequest);

        bookingRequest.setOnSucceeded(event -> HotelApp.getNavigator().navigate(Scenes.USER_BOOKINGS));

        bookingRequest.setOnFailed(event -> {
            throw new BookingRequestException("There was an error at booking service attempt: " + event.getSource().getMessage());
        });
    }
}
