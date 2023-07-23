package com.alura.hotel.controllers;

import com.alura.hotel.HotelApp;
import com.alura.hotel.enums.ActionType;
import com.alura.hotel.enums.Scenes;
import com.alura.hotel.exceptions.AuthenticationException;
import com.alura.hotel.router.Navigator;
import com.alura.hotel.services.AuthService;
import com.alura.hotel.session.SessionManagerFactory;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOError;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class LoginViewController extends BaseController {

    @Autowired
    private AuthService authService;

    @FXML
    public MFXButton backButton;

    @FXML
    private MFXTextField usernameLoginField;

    @FXML
    private MFXPasswordField passwordLoginField;

    @FXML
    private MFXButton loginButton;

    @FXML
    void navigateToSignUp(MouseEvent event) {
        Navigator navigator = HotelApp.getNavigator();
        navigator.navigate(Scenes.SIGN_UP);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        handleCredentials(loginButton, usernameLoginField, passwordLoginField);
    }

    private void handleCredentials(MFXButton loginButton, MFXTextField usernameLoginField, MFXPasswordField passwordLoginField) {
        loginButton.setOnAction(event -> {
            String username = usernameLoginField.getText();
            String password = passwordLoginField.getText();

            authService.attemptLogin(username, password, authResponse -> {
                SessionManagerFactory.getInstance().createSession(authResponse);
                Platform.runLater(() -> {
                    HotelApp.getNavigator().navigate(Scenes.HOME);
                });
            }, failedEvent -> {
                showSnackbar("Wrong username or password", ActionType.ERROR);
            });
        });
        
        backButton.setOnMousePressed(event -> {
            HotelApp.getNavigator().navigate(Scenes.MAIN);
        });
    }
}