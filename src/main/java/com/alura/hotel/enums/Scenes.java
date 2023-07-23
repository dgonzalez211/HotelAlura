package com.alura.hotel.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Scenes {

    MAIN("MainView"),
    HOME("HomeView"),
    LOGIN("auth/LoginView"),
    SIGN_UP("auth/SignUpView"),
    BOOKING_INFORMATION("booking/BookingInformationView"),
    BOOKING_CONFIRMATION("booking/BookingConfirmationView"),
    HOTEL_LIST("hotel/HotelListView"),
    ROOM_LIST("room/RoomListView"),
    USER_ACCOUNT("user/UserAccountView"),
    USER_BOOKINGS("user/UserBookingsView");

    private final String sceneFilename;

    Scenes(String sceneFilename) {
        this.sceneFilename = sceneFilename;
    }

    public String getSceneFilename() {
        return sceneFilename;
    }

    public static List<String> getAllScenes() {
        return Arrays.stream(values())
                .map(Scenes::getSceneFilename)
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return sceneFilename;
    }
}
