package com.alura.hotel.controllers;

import com.alura.hotel.HotelApp;
import com.alura.hotel.HotelAppResourcesLoader;
import com.alura.hotel.components.UserBookingCard;
import com.alura.hotel.dto.BookingDto;
import com.alura.hotel.enums.ActionType;
import com.alura.hotel.enums.Scenes;
import com.alura.hotel.services.BookingService;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import io.github.palexdev.materialfx.utils.ScrollUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class UserBookingsViewController extends BaseController {

    @Autowired
    public BookingService bookingService;

    @FXML
    public VBox mainContainer;

    @FXML
    public MFXScrollPane bookingsScrollPane;

    @FXML
    public MFXButton backButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        VBox bookingsCardContainer = new VBox();
        bookingsCardContainer.setSpacing(10);
        bookingsCardContainer.setPadding(new Insets(10));
        bookingsCardContainer.setAlignment(Pos.CENTER);

        bookingService.findAll((bookings) -> {
            if (bookings.isEmpty()) {
                showSnackbar("There's no bookings in your account", ActionType.WARNING);
                return;
            }
            for (BookingDto booking : bookings) {
                Image headerImage = new Image(HotelAppResourcesLoader.getRandomRoomImage().toExternalForm());
                UserBookingCard cardView = new UserBookingCard(booking, headerImage, this);
                cardView.setMaxWidth(400);
                bookingsCardContainer.getChildren().add(cardView);
            }
        });

        bookingsScrollPane.setContent(bookingsCardContainer);
        bookingsScrollPane.setFitToWidth(true);
        ScrollUtils.addSmoothScrolling(bookingsScrollPane);

        mainContainer.setPadding(new Insets(10));
        mainContainer.setAlignment(Pos.CENTER);

        backButton.setOnMousePressed(event -> {
            HotelApp.getNavigator().navigate(Scenes.HOME);
        });
    }
}