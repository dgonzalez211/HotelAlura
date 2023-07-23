package com.alura.hotel.controllers;

import com.alura.hotel.HotelApp;
import com.alura.hotel.enums.Scenes;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class MainViewController extends BaseController {

    @FXML
    private MFXButton signInButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);

        signInButton.setOnMousePressed(event -> {
            HotelApp.getNavigator().navigate(Scenes.LOGIN);
        });

    }


}
