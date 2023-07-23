package com.alura.hotel.controllers;

import com.alura.hotel.HotelApp;
import com.alura.hotel.dto.UserRegistrationDto;
import com.alura.hotel.enums.Scenes;
import com.alura.hotel.exceptions.AuthenticationException;
import com.alura.hotel.router.Navigator;
import com.alura.hotel.services.AuthService;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class SignUpViewController extends BaseController {

    @Autowired
    private AuthService authService;

    @FXML
    public MFXButton backButton;

    @FXML
    private MFXTextField firstNameField;

    @FXML
    private MFXTextField lastNameField;

    @FXML
    private MFXTextField usernameSignUpField;

    @FXML
    private MFXPasswordField passwordSignUpField;

    @FXML
    private MFXTextField phoneNumberField;

    @FXML
    private MFXTextField emailField;

    @FXML
    void handleSignUp(MouseEvent event) {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String username = usernameSignUpField.getText();
        String password = passwordSignUpField.getText();
        String phoneNumber = phoneNumberField.getText();
        String email = emailField.getText();

        UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
        userRegistrationDto.setFirstName(firstName);
        userRegistrationDto.setLastName(lastName);
        userRegistrationDto.setUsername(username);
        userRegistrationDto.setPassword(password);
        userRegistrationDto.setPhoneNumber(phoneNumber);
        userRegistrationDto.setEmail(email);

        try {
            authService.attemptSignUp(userRegistrationDto);
            HotelApp.getNavigator().navigate(Scenes.LOGIN);
        } catch (AuthenticationException exception) {

        }

    }

    @FXML
    void navigateToLogin(MouseEvent event) {
        Navigator navigator = HotelApp.getNavigator();
        navigator.navigate(Scenes.LOGIN);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        backButton.setOnMousePressed(event -> {
            HotelApp.getNavigator().navigate(Scenes.MAIN);
        });
    }
}
