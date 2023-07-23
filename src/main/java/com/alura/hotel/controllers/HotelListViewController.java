package com.alura.hotel.controllers;

import com.alura.hotel.HotelApp;
import com.alura.hotel.HotelAppResourcesLoader;
import com.alura.hotel.components.HotelCard;
import com.alura.hotel.dto.HotelDto;
import com.alura.hotel.enums.Scenes;
import com.alura.hotel.services.HotelService;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import io.github.palexdev.materialfx.css.themes.MFXThemeManager;
import io.github.palexdev.materialfx.css.themes.Themes;
import io.github.palexdev.materialfx.utils.ScrollUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class HotelListViewController extends BaseController {

    @Autowired
    HotelService hotelService;
    @FXML
    public MFXButton backButton;
    @FXML
    public MFXScrollPane hotelsScrollpane;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        HBox hotelsCardContainer = new HBox();
        hotelsCardContainer.setPrefWidth(700);
        hotelsCardContainer.setFillHeight(true);
        hotelsCardContainer.setPadding(new Insets(10));
        hotelsCardContainer.setAlignment(Pos.CENTER);
        hotelsCardContainer.setSpacing(50);
        MFXThemeManager.addOn(hotelsCardContainer, Themes.DEFAULT, Themes.LEGACY);

        hotelService.findAll((hotels) -> {
            for (HotelDto hotel : hotels) {
                HotelCard cardView = new HotelCard(hotel, new Image(HotelAppResourcesLoader.getRandomHotelImage().toExternalForm()));
                hotelsCardContainer.getChildren().add(cardView);
            }
        });

        hotelsScrollpane.setContent(hotelsCardContainer);
        hotelsScrollpane.setFitToHeight(true);
        hotelsScrollpane.setFitToWidth(false);
        hotelsScrollpane.setPannable(true);
        hotelsScrollpane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        hotelsScrollpane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        ScrollUtils.addSmoothScrolling(hotelsScrollpane);

        backButton.setOnMousePressed(event -> {
            HotelApp.getNavigator().navigate(Scenes.HOME);
        });
    }
}
