package com.alura.hotel.config;

import com.alura.hotel.services.impl.AuthServiceImpl;
import com.alura.hotel.services.impl.BookingServiceImpl;
import com.alura.hotel.services.impl.HotelServiceImpl;
import com.alura.hotel.services.impl.RoomServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public AuthServiceImpl authService() {
        return new AuthServiceImpl();
    }

    @Bean
    public BookingServiceImpl bookingService() {
        return new BookingServiceImpl();
    }

    @Bean
    public HotelServiceImpl hotelService() {
        return new HotelServiceImpl();
    }

    @Bean
    public RoomServiceImpl roomService() {
        return new RoomServiceImpl();
    }

}
