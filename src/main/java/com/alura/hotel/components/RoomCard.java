package com.alura.hotel.components;

import com.alura.hotel.HotelApp;
import com.alura.hotel.HotelAppResourcesLoader;
import com.alura.hotel.dto.RoomDto;
import com.alura.hotel.enums.Scenes;
import com.alura.hotel.util.CacheManagerUtil;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.enums.ButtonType;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class RoomCard extends VBox {

    public RoomCard(RoomDto roomDto, Image image) {
        getStylesheets().add(HotelAppResourcesLoader.loadURL("css/style.css").toExternalForm());
        getStyleClass().add("material-design-card");

        setWidth(510);
        setHeight(680);

        HBox imageContainer = new HBox();
        imageContainer.getStyleClass().add("card-image");

        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.getStyleClass().add("card-image");

        Rectangle2D viewportRect = new Rectangle2D(50, 50, 300, 500);
        imageView.setViewport(viewportRect);
        imageContainer.getChildren().add(imageView);

        Label nameLabel = new Label(roomDto.getName());
        nameLabel.getStyleClass().add("header-label");

        Label descriptionLabel = new Label(roomDto.getDescription());
        descriptionLabel.getStyleClass().add("body-label");

        Label priceLabel = new Label("$" + roomDto.getPrice() + "/night");
        priceLabel.getStyleClass().add("body-label");

        MFXButton bookButton = new MFXButton("Book");
        bookButton.setButtonType(ButtonType.RAISED);
        bookButton.setStyle("-fx-background-color: #088eda; -fx-text-fill: #ffffff; -fx-background-radius: 20;");
        bookButton.setPrefSize(100, 50);
        bookButton.setOnMousePressed(event -> {
            CacheManagerUtil.getInstance().put("selectedRoom", roomDto);
            HotelApp.getNavigator().navigate(Scenes.BOOKING_INFORMATION);
        });

        VBox detailsContainer = new VBox(nameLabel, descriptionLabel, priceLabel);
        detailsContainer.setAlignment(Pos.BOTTOM_LEFT);
        VBox.setVgrow(detailsContainer, Priority.ALWAYS);

        HBox buttonContainer = new HBox(bookButton);
        buttonContainer.setAlignment(Pos.BOTTOM_RIGHT);
        buttonContainer.setStyle("-fx-background-radius: 60");
        buttonContainer.setAlignment(Pos.BOTTOM_RIGHT);

        getChildren().addAll(imageContainer, detailsContainer, buttonContainer);
    }

}
