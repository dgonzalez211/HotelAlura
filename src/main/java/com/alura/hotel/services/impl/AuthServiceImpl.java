package com.alura.hotel.services.impl;

import com.alura.hotel.HotelApp;
import com.alura.hotel.api.ApiEndpoints;
import com.alura.hotel.dto.AuthSessionDto;
import com.alura.hotel.dto.UserRegistrationDto;
import com.alura.hotel.enums.Scenes;
import com.alura.hotel.exceptions.AuthenticationException;
import com.alura.hotel.services.AuthService;
import com.alura.hotel.services.request.RequestResultSingle;
import com.alura.hotel.session.SessionManagerFactory;
import com.alura.hotel.util.RestClientUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    public void attemptLogin(String username, String password) {
        attemptLogin(username, password, null, null);
    }
    public void attemptLogin(
            String username, String password,
            RequestResultSingle<AuthSessionDto> onSuccess, EventHandler<WorkerStateEvent> onFailed) {

        JsonObject loginParameters = new JsonObject();
        loginParameters.addProperty("username", username);
        loginParameters.addProperty("password", password);

        String bodyRequest = new Gson().toJson(loginParameters);

        RestClientUtil client = new RestClientUtil();
        Task<String> loginRequest = client.post(ApiEndpoints.SIGNIN_API_URL, bodyRequest);

        if (onSuccess != null) {
            loginRequest.setOnSucceeded(event -> {
                String response = loginRequest.getValue();
                AuthSessionDto authResponse = new Gson().fromJson(response, AuthSessionDto.class);
                onSuccess.accept(authResponse);
            });
        }

        if (onFailed != null) {
            loginRequest.setOnFailed(onFailed);
        }

    }

    @Override
    public void attemptSignUp(UserRegistrationDto userRegistrationDto) {
        String bodyRequest = new Gson().toJson(userRegistrationDto);

        RestClientUtil client = new RestClientUtil();
        Task<String> signUpRequest = client.post(ApiEndpoints.SIGNUP_API_URL, bodyRequest);

        signUpRequest.setOnFailed(event -> {
            throw new AuthenticationException("There was an error at sign up request: " + event.getSource().getException().getMessage());
        });
    }

    public boolean isAuthenticated() {
        return SessionManagerFactory.getInstance().isAuthenticated();
    }

    public String getUsername() {
        return SessionManagerFactory.getInstance().getUsername();
    }

    public void attemptLogout() {
        SessionManagerFactory.getInstance().invalidateSession();
    }
}
