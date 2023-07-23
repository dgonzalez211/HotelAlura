package com.alura.hotel;

import com.alura.hotel.enums.Scenes;
import com.alura.hotel.router.Navigator;
import io.github.palexdev.materialfx.css.themes.MFXThemeManager;
import io.github.palexdev.materialfx.css.themes.Themes;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;
import java.net.URL;

@SpringBootApplication
public class HotelApp extends Application {

    private static Navigator navigator;

    @Override
    public void start(Stage stage) throws IOException {
        ConfigurableApplicationContext context = SpringApplication.run(HotelApp.class);
        context.getAutowireCapableBeanFactory().autowireBean(this);

        FXMLLoader fxmlLoader = new FXMLLoader(HotelApp.class.getResource("view/MainView.fxml"));
        fxmlLoader.setControllerFactory(context::getBean);

        Scene scene = new Scene(fxmlLoader.load());
        scene.setFill(Color.TRANSPARENT);
        scene.getStylesheets().add(HotelAppResourcesLoader.load("css/style.css"));
        MFXThemeManager.addOn(scene, Themes.DEFAULT, Themes.LEGACY);

        startNavigator(stage, context);
        loadAllViews();
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Alura Hotel");
        stage.setScene(scene);
        stage.show();
    }

    private void startNavigator(Stage stage, ConfigurableApplicationContext context) {
        navigator = new Navigator();
        navigator.setPrimaryStage(stage);
        navigator.setApplicationContext(context);
    }

    private void loadAllViews() {
        for (String sceneFilename : Scenes.getAllScenes()) {
            URL viewUrl = HotelAppResourcesLoader.loadURL("view/" + sceneFilename + ".fxml");
            navigator.addScreen(sceneFilename, viewUrl);
        }
    }

    public static Navigator getNavigator() {
        return navigator;
    }

    public static void main(String[] args) {
        launch(args);
    }
}