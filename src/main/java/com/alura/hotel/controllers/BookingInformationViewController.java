package com.alura.hotel.controllers;

import com.alura.hotel.HotelApp;
import com.alura.hotel.HotelAppResourcesLoader;
import com.alura.hotel.dto.AuthSessionDto;
import com.alura.hotel.dto.BookingDto;
import com.alura.hotel.dto.HotelDto;
import com.alura.hotel.dto.RoomDto;
import com.alura.hotel.enums.ActionType;
import com.alura.hotel.enums.Scenes;
import com.alura.hotel.session.SessionManagerFactory;
import com.alura.hotel.util.CacheManagerUtil;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

@Controller
public class BookingInformationViewController extends BaseController {

    @FXML
    private MFXDatePicker checkInDatePicker;

    @FXML
    private MFXDatePicker checkOutDatePicker;

    @FXML
    private MFXButton decreaseGuestsButton;

    @FXML
    private Label guestsCountLabel;

    @FXML
    private Label guestsNumberLabel;

    @FXML
    private ImageView hotelImage;

    @FXML
    private Label hotelLocationLabel;

    @FXML
    private Label hotelNameLabel;

    @FXML
    private MFXButton increaseGuestsButton;

    @FXML
    private MFXButton payButton;

    @FXML
    private Label roomDescriptionLabel;

    @FXML
    private ImageView roomImage;

    @FXML
    private Label roomNameLabel;

    @FXML
    private Label roomPriceLabel;

    @FXML
    private Label roomTypeLabel;

    private BookingDto bookingDto;

    private int guestCount = 1;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        CacheManagerUtil instance = CacheManagerUtil.getInstance();

        HotelDto hotelDto = (HotelDto) instance.get("selectedHotel");
        RoomDto roomDto = (RoomDto) instance.get("selectedRoom");
        Long userId = SessionManagerFactory.getInstance().getUserId();

        if (hotelDto == null || roomDto == null) {
            return;
        }

        increaseGuestsButton.setOnMousePressed(event -> {
            if (guestCount < 8) {
                guestCount = guestCount + 1;
                guestsCountLabel.setText(String.format("%d Guests", guestCount));
                guestsNumberLabel.setText(String.format("%d", guestCount));
            }
        });

        decreaseGuestsButton.setOnMousePressed(event -> {
            if (guestCount > 1) {
                guestCount = guestCount - 1;
                guestsCountLabel.setText(String.format("%d Guests", guestCount));
                guestsNumberLabel.setText(String.format("%d", guestCount));
            }
        });

        hotelImage.setImage(new Image(HotelAppResourcesLoader.getRandomHotelImage().toExternalForm()));
        hotelNameLabel.setText(hotelDto.getName());
        hotelLocationLabel.setText(hotelDto.getLocation());

        roomNameLabel.setText(roomDto.getName());
        roomTypeLabel.setText(roomDto.getType());
        roomDescriptionLabel.setText(roomDto.getDescription());
        roomImage.setImage(new Image(HotelAppResourcesLoader.getRandomRoomImage().toExternalForm()));
        roomPriceLabel.setText(String.format("$%f/night", roomDto.getPrice()));


        payButton.setOnMousePressed(event -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            LocalDate checkInDate = checkInDatePicker.getValue();
            LocalDate checkOutDate = checkInDatePicker.getValue();

            if (checkInDate == null || checkOutDate == null) {
                showSnackbar("Please select check in and checkout dates", ActionType.ERROR);
                return;
            }

            String checkIn = formatter.format(checkInDatePicker.getValue());
            String checkOut = formatter.format(checkOutDatePicker.getValue());

            bookingDto = new BookingDto();
            bookingDto.setCheckIn(checkIn);
            bookingDto.setCheckOut(checkOut);
            bookingDto.setGuests(guestCount);
            bookingDto.setStatus("PENDING");
            bookingDto.setHotelDto(hotelDto);
            bookingDto.setRoomDto(roomDto);
            bookingDto.setUserId(userId);
            instance.put("currentBooking", bookingDto);
            HotelApp.getNavigator().navigate(Scenes.BOOKING_CONFIRMATION);
        });

    }
}
