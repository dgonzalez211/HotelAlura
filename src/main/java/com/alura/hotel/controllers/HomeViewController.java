package com.alura.hotel.controllers;

import com.alura.hotel.HotelApp;
import com.alura.hotel.HotelAppResourcesLoader;
import com.alura.hotel.components.HotelRecommendationCard;
import com.alura.hotel.dto.HotelDto;
import com.alura.hotel.enums.Scenes;
import com.alura.hotel.services.AuthService;
import com.alura.hotel.services.HotelService;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import io.github.palexdev.materialfx.utils.ScrollUtils;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class HomeViewController extends BaseController {

    @Autowired
    private AuthService authService;
    @Autowired
    private HotelService hotelService;

    @FXML
    public ImageView userImage;
    @FXML
    public Label usernameLabel;
    @FXML
    public VBox mainContainer;
    @FXML
    public MFXButton findButton;
    @FXML
    public MFXScrollPane recommendationsScrollPane;
    @FXML
    public MFXButton homeButton;
    @FXML
    public MFXButton bookingsButton;
    @FXML
    public MFXButton accountButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);

        if (!authService.isAuthenticated()) {
            return;
        }

        VBox hotelsCardContainer = new VBox();
        hotelsCardContainer.setSpacing(10);
        hotelsCardContainer.setPadding(new Insets(10));
        hotelsCardContainer.setAlignment(Pos.CENTER);

        hotelService.findAll((hotels) -> {
            for (HotelDto hotel : hotels) {
                Image headerImage = new Image(HotelAppResourcesLoader.getRandomHotelImage().toExternalForm());
                HotelRecommendationCard cardView = new HotelRecommendationCard(hotel, headerImage);
                hotelsCardContainer.getChildren().add(cardView);
            }
        });

        recommendationsScrollPane.setContent(hotelsCardContainer);
        recommendationsScrollPane.setFitToWidth(true);
        ScrollUtils.addSmoothScrolling(recommendationsScrollPane);

        mainContainer.setPadding(new Insets(10));
        mainContainer.setAlignment(Pos.CENTER);

        usernameLabel.setText(authService.getUsername());

        findButton.setOnMousePressed(event -> {
            HotelApp.getNavigator().navigate(Scenes.HOTEL_LIST);
        });

        bookingsButton.setOnMousePressed(event -> {
            HotelApp.getNavigator().navigate(Scenes.USER_BOOKINGS);
        });

        accountButton.setOnMousePressed(event -> {
            HotelApp.getNavigator().navigate(Scenes.USER_ACCOUNT);
        });
    }

}
