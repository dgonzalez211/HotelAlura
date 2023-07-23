package com.alura.hotel.controllers;

import com.alura.hotel.HotelApp;
import com.alura.hotel.HotelAppResourcesLoader;
import com.alura.hotel.components.RoomCard;
import com.alura.hotel.dto.HotelDto;
import com.alura.hotel.dto.RoomDto;
import com.alura.hotel.enums.Scenes;
import com.alura.hotel.util.CacheManagerUtil;
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
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Controller
public class RoomListViewController extends BaseController {

    @FXML
    public MFXButton backButton;
    @FXML
    public MFXScrollPane roomsScrollPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        CacheManagerUtil instance = CacheManagerUtil.getInstance();

        HotelDto hotelDto = (HotelDto) instance.get("selectedHotel");

        if (hotelDto == null) {
            return;
        }

        HBox roomsCardContainer = createCardContainer();

        Image headerImage = new Image(HotelAppResourcesLoader.getRandomRoomImage().toExternalForm());
        List<RoomDto> rooms = hotelDto.getRooms();

        for (RoomDto room : rooms) {
            RoomCard cardView = new RoomCard(room, headerImage);
            cardView.setMaxWidth(360);
            cardView.setMinWidth(360);
            roomsCardContainer.getChildren().add(cardView);
        }
        roomsScrollPane.setContent(roomsCardContainer);

        adjustScrollPane();

        backButton.setOnMousePressed(event -> {
            HotelApp.getNavigator().navigate(Scenes.HOTEL_LIST);
        });
    }

    private HBox createCardContainer() {
        HBox cardContainer = new HBox();
        cardContainer.setPadding(new Insets(10));
        cardContainer.setAlignment(Pos.CENTER);
        cardContainer.setSpacing(50);
        return cardContainer;
    }

    private void adjustScrollPane() {
        roomsScrollPane.setFitToHeight(false);
        roomsScrollPane.setFitToWidth(false);
        roomsScrollPane.setPannable(true);
        roomsScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        roomsScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        ScrollUtils.addSmoothScrolling(roomsScrollPane);
    }
}
