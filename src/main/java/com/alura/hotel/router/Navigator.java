package com.alura.hotel.router;

import com.alura.hotel.enums.Scenes;
import io.github.palexdev.materialfx.css.themes.MFXThemeManager;
import io.github.palexdev.materialfx.css.themes.Themes;
import javafx.animation.*;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

import static io.github.palexdev.mfxeffects.utils.StyleUtils.setBackground;

public final class Navigator {

    private final HashMap<String, URL> screenMap = new HashMap<>();

    private Stage stage;

    private ConfigurableApplicationContext applicationContext;

    public void navigate(Scenes scene) {
        FXMLLoader loader = new FXMLLoader(screenMap.get(scene.getSceneFilename()));
        loader.setControllerFactory(applicationContext::getBean);
        try {
            Parent root = loader.load();
            Scene scenePane = new Scene(root);
            scenePane.setFill(Color.TRANSPARENT);
            MFXThemeManager.addOn(scenePane, Themes.DEFAULT, Themes.LEGACY);

            FadeTransition fadeTransition = new FadeTransition();
            fadeTransition.setDuration(Duration.millis(500));
            fadeTransition.setNode(root);
            fadeTransition.setFromValue(0);
            fadeTransition.setToValue(1);
            fadeTransition.play();

            stage.setScene(scenePane);
            stage.show();
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
            exception.printStackTrace();
        }
    }

    public void setApplicationContext(ConfigurableApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public void setPrimaryStage(Stage stage) {
        this.stage = stage;
    }

    public void addScreen(String name, URL scene) {
        screenMap.put(name, scene);
    }

    public URL getScreen(String name) {
        return screenMap.get(name);
    }

    public void removeScreen(String name) {
        screenMap.remove(name);
    }


}
