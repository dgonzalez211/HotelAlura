package com.alura.hotel.services;

import com.alura.hotel.dto.AuthSessionDto;
import com.alura.hotel.dto.UserRegistrationDto;
import com.alura.hotel.exceptions.AuthenticationException;
import com.alura.hotel.services.request.RequestResultSingle;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;

public interface AuthService {

    void attemptLogin(String username, String password);
    void attemptLogin(String username, String password, RequestResultSingle<AuthSessionDto> onSuccess, EventHandler<WorkerStateEvent> onFailed);

    void attemptSignUp(UserRegistrationDto userRegistrationDto) throws AuthenticationException;

    void attemptLogout() throws AuthenticationException;

    boolean isAuthenticated() throws AuthenticationException;

    String getUsername();
}
