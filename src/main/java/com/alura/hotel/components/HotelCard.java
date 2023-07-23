package com.alura.hotel.components;

import com.alura.hotel.HotelApp;
import com.alura.hotel.HotelAppResourcesLoader;
import com.alura.hotel.dto.HotelDto;
import com.alura.hotel.enums.Scenes;
import com.alura.hotel.util.CacheManagerUtil;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.enums.ButtonType;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.Random;

public class HotelCard extends VBox {

    public HotelCard(HotelDto hotelDto, Image image) {

        getStylesheets().add(HotelAppResourcesLoader.loadURL("css/style.css").toExternalForm());
        getStyleClass().add("material-design-card");

        setWidth(510);
        setHeight(680);

        HBox imageContainer = new HBox();
        imageContainer.getStyleClass().add("card-image");

        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.getStyleClass().add("card-image");

        Rectangle2D viewportRect = new Rectangle2D(50, 50, 340, 500);
        imageView.setViewport(viewportRect);
        imageContainer.getChildren().add(imageView);

        Label nameLabel = new Label(hotelDto.getName());
        nameLabel.getStyleClass().add("header-label");

        Label locationLabel = new Label(hotelDto.getLocation());
        locationLabel.getStyleClass().add("header-label");

        int randomPrice = new Random().nextInt(401) + 100;
        Label priceLabel = new Label("$" + randomPrice + "/nights");
        priceLabel.getStyleClass().add("body-label");

        MFXButton bookButton = new MFXButton("Book now");
        bookButton.setButtonType(ButtonType.RAISED);
        bookButton.setStyle("-fx-background-color: #088eda; -fx-text-fill: #ffffff; -fx-background-radius: 20;");
        bookButton.setPrefSize(100, 300);
        bookButton.setOnMousePressed(event -> {
            CacheManagerUtil.getInstance().put("selectedHotel", hotelDto);
            HotelApp.getNavigator().navigate(Scenes.ROOM_LIST);
        });

        VBox detailsContainer = new VBox(nameLabel, locationLabel, priceLabel);
        detailsContainer.setAlignment(Pos.BOTTOM_LEFT);
        VBox.setVgrow(detailsContainer, Priority.ALWAYS);

        HBox buttonContainer = new HBox(bookButton);
        buttonContainer.setAlignment(Pos.BOTTOM_RIGHT);
        buttonContainer.setStyle("-fx-background-radius: 60");
        buttonContainer.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));


        getChildren().addAll(imageContainer, detailsContainer, buttonContainer);
    }
}
