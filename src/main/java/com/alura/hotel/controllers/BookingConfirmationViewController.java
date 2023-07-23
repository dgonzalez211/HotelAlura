package com.alura.hotel.controllers;

import com.alura.hotel.HotelApp;
import com.alura.hotel.dto.BookingDto;
import com.alura.hotel.enums.ActionType;
import com.alura.hotel.enums.BookingStatusType;
import com.alura.hotel.enums.Scenes;
import com.alura.hotel.exceptions.BookingRequestException;
import com.alura.hotel.services.BookingService;
import com.alura.hotel.util.CacheManagerUtil;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class BookingConfirmationViewController extends BaseController {

    @Autowired
    BookingService bookingService;

    @FXML
    public Label paymentAmountLabel;
    @FXML
    public MFXButton payButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        CacheManagerUtil instance = CacheManagerUtil.getInstance();

        BookingDto bookingDto = (BookingDto) instance.get("currentBooking");

        if (bookingDto == null) {
            return;
        }

        payButton.setOnMousePressed(event -> {
            bookingDto.setStatus(BookingStatusType.CONFIRMED.name());
            try {
                bookingService.save(bookingDto);
                HotelApp.getNavigator().navigate(Scenes.HOME);
                showSnackbar("Your payment has been approved", ActionType.SUCCESS);
            } catch (BookingRequestException exception) {

            }

        });
    }
}
