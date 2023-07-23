package com.alura.hotel.components;

import com.alura.hotel.HotelAppResourcesLoader;
import com.alura.hotel.controllers.UserBookingsViewController;
import com.alura.hotel.dto.BookingDto;
import com.alura.hotel.enums.ActionType;
import com.alura.hotel.enums.BookingStatusType;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.Objects;

public class UserBookingCard extends HBox {

    public UserBookingCard(BookingDto bookingDto, Image image, UserBookingsViewController controller) {
        setMaxWidth(400);
        getStylesheets().add(Objects.requireNonNull(HotelAppResourcesLoader.loadURL("css/style.css")).toExternalForm());

        getStyleClass().add("material-design-card");

        ImageView headerImageView = new ImageView(image);
        headerImageView.setPreserveRatio(true);
        headerImageView.setFitWidth(160);

        VBox imageContainer = new VBox(headerImageView);
        imageContainer.getStyleClass().add("card-image");
        imageContainer.setAlignment(Pos.CENTER);
        imageContainer.getStyleClass().add("image-container-card-box");
        VBox.setVgrow(headerImageView, Priority.ALWAYS);

        VBox bodyCardContainer = new VBox();
        bodyCardContainer.setAlignment(Pos.CENTER_LEFT);
        bodyCardContainer.getStyleClass().add("body-container-card-box");

        HBox optionsContainer = new HBox();
        optionsContainer.setAlignment(Pos.BOTTOM_CENTER);
        optionsContainer.setPadding(new Insets(20));
        optionsContainer.setSpacing(10);
        optionsContainer.getStyleClass().add("options-container-card-box");

        MFXButton cancelBookingButton = new MFXButton("Cancel");
        MFXButton removeBookingButton = new MFXButton("Remove");

        optionsContainer.getChildren().addAll(cancelBookingButton, removeBookingButton);

        Label titleLabel = new Label(bookingDto.getHotelDto().getName());
        titleLabel.getStyleClass().add("header-label");

        Label locationLabel = new Label(bookingDto.getHotelDto().getLocation());
        FontAwesomeIconView locationIcon = new FontAwesomeIconView();
        locationIcon.setIcon(FontAwesomeIcon.LOCATION_ARROW);
        locationIcon.setFill(Color.web("#078fdb"));
        locationIcon.setSize("24px");
        locationLabel.setGraphic(locationIcon);
        locationLabel.getStyleClass().add("body-label");

        Label dateLabel = new Label(bookingDto.getCheckIn().toString());
        dateLabel.getStyleClass().add("body-label");
        FontAwesomeIconView calendarIcon = new FontAwesomeIconView();
        calendarIcon.setIcon(FontAwesomeIcon.CALENDAR);
        calendarIcon.setFill(Color.web("#078fdb"));
        calendarIcon.setSize("24px");
        dateLabel.setGraphic(calendarIcon);

        Label statusLabel = new Label(bookingDto.getStatus());
        if (bookingDto.getStatus().equals(BookingStatusType.CONFIRMED.name())) {
            statusLabel.setStyle("-fx-text-fill: GREEN");
        } else if (bookingDto.getStatus().equals(BookingStatusType.CANCELLED.name())) {
            statusLabel.setStyle("-fx-text-fill: RED");
        } else {
            statusLabel.setStyle("-fx-text-fill: YELLOW");
        }
        FontAwesomeIconView statusIcon = new FontAwesomeIconView();
        statusIcon.setIcon(FontAwesomeIcon.ADDRESS_CARD);
        statusIcon.setFill(Color.web("#078fdb"));
        statusLabel.setGraphic(statusIcon);

        bodyCardContainer.getChildren().addAll(titleLabel, locationLabel, dateLabel, statusLabel, optionsContainer);

        VBox.setVgrow(titleLabel, Priority.ALWAYS);

        getChildren().addAll(imageContainer, bodyCardContainer);

        MFXDialog cancelBookingDialog = controller.getBasicDialog("Cancel Booking", "Are you sure woy want to cancel your booking?");

        cancelBookingDialog.addActionButton(new MFXButton("Cancel"), ActionType.CANCEL, event -> {
            cancelBookingDialog.close();
        });

        cancelBookingDialog.addActionButton(new MFXButton("Confirm"), ActionType.CONFIRM, event -> {
            bookingDto.setStatus(BookingStatusType.CANCELLED.name());
            controller.bookingService.update(bookingDto);
            cancelBookingDialog.close();
        });

        MFXDialog removeBookingDialog = controller.getBasicDialog("Delete Booking", "Are you sure woy want to remove this booking?");

        removeBookingDialog.addActionButton(new MFXButton("Cancel"), ActionType.CANCEL, event -> {
            removeBookingDialog.close();
        });

        removeBookingDialog.addActionButton(new MFXButton("Confirm"), ActionType.CONFIRM, event -> {
            controller.bookingService.delete(bookingDto.getId());
            removeBookingDialog.close();
        });


        cancelBookingButton.setOnMousePressed(event -> {
            if (bookingDto.getStatus().equals("CANCELLED")) {
                return;
            }
            cancelBookingDialog.show();
        });

        removeBookingButton.setOnMousePressed(event -> {
            removeBookingDialog.show();
        });
    }


}