package com.alura.hotel.controllers;

import com.alura.hotel.HotelApp;
import com.alura.hotel.enums.Scenes;
import com.alura.hotel.router.Navigator;
import com.alura.hotel.services.AuthService;
import com.alura.hotel.session.SessionManagerFactory;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class UserAccountViewController extends BaseController {

    @Autowired
    private AuthService authService;

    @FXML
    public Label usernameLabel;
    @FXML
    public MFXButton backButton;
    @FXML
    public MFXButton logoutButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);

        usernameLabel.setText(SessionManagerFactory.getInstance().getUsername());

        backButton.setOnMousePressed(event -> {
            HotelApp.getNavigator().navigate(Scenes.HOME);
        });

        logoutButton.setOnMousePressed(event -> {
            authService.attemptLogout();
            Navigator navigator = HotelApp.getNavigator();
            navigator.navigate(Scenes.LOGIN);
        });
    }
}