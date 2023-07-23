package com.alura.hotel.api;

public class ApiEndpoints {

    public static final String BASE_URL = "http://localhost:8080/api/v1";

    // Authentication paths
    public static final String SIGNIN_API_URL = BASE_URL + "/auth/signin";
    public static final String SIGNUP_API_URL = BASE_URL + "/auth/signup";

    // Rooms management paths
    public static final String ROOMS_URL = BASE_URL + "/rooms";

    // Hotels management paths
    public static final String HOTELS_URL = BASE_URL + "/hotels";

    // Bookings management paths
    public static final String BOOKINGS_URL = BASE_URL + "/reservations";
    public static final String USER_BOOKINGS_URL = BASE_URL + "/reservations/user?username=";
    public static final String BOOKING_UPDATE_URL = BASE_URL + "/reservations/";
    public static final String BOOKING_DELETE_URL = BASE_URL + "/reservations/";
}
