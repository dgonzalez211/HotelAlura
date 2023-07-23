package com.alura.hotel.components;

import com.alura.hotel.HotelAppResourcesLoader;
import com.alura.hotel.dto.HotelDto;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.Objects;
import java.util.Random;

public class HotelRecommendationCard extends HBox {

    public HotelRecommendationCard(HotelDto hotel, Image image) {

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

        Label titleLabel = new Label(hotel.getName());
        titleLabel.getStyleClass().add("title");
        titleLabel.getStyleClass().add("header-label");

        Label locationLabel = new Label(hotel.getLocation());
        FontAwesomeIconView locationIcon = new FontAwesomeIconView(FontAwesomeIcon.LOCATION_ARROW);
        locationIcon.setFill(Color.web("#078fdb"));
        locationIcon.setSize("24px");
        locationLabel.setGraphic(locationIcon);
        locationLabel.getStyleClass().add("body-label");

        Label ratingLabel = new Label(hotel.getRating().toString());
        FontAwesomeIconView ratingIcon = new FontAwesomeIconView(FontAwesomeIcon.STAR);
        ratingIcon.setFill(Color.web("#078fdb"));
        ratingIcon.setSize("24px");
        ratingLabel.setGraphic(ratingIcon);
        ratingLabel.getStyleClass().add("body-label");

        Random random = new Random();
        int randomPrice = random.nextInt(201) + 100;

        Label priceLabel = new Label("$" + randomPrice + "/night");
        priceLabel.getStyleClass().add("body-label");

        bodyCardContainer.getChildren().addAll(titleLabel, locationLabel, ratingLabel, priceLabel);

        VBox.setVgrow(titleLabel, Priority.ALWAYS);

        getChildren().addAll(imageContainer, bodyCardContainer);
    }
}