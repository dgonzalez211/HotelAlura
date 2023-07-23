package com.alura.hotel.controllers;

import com.alura.hotel.components.MFXDialog;
import com.alura.hotel.components.Snackbar;
import com.alura.hotel.enums.ActionType;
import io.github.palexdev.materialfx.enums.ScrimPriority;
import io.github.palexdev.mfxresources.fonts.MFXFontIcon;
import javafx.application.Platform;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public abstract class BaseController implements Initializable {

    @FXML
    public AnchorPane rootPane;
    @FXML
    public MFXFontIcon minimizeIcon;
    @FXML
    public MFXFontIcon closeIcon;
    @FXML
    public MFXFontIcon alwaysOnTopIcon;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeWindowControllers();
    }

    protected void initializeWindowControllers() {
        closeIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> Platform.exit());
        minimizeIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> ((Stage) rootPane.getScene().getWindow()).setIconified(true));
        alwaysOnTopIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            Stage stage = ((Stage) rootPane.getScene().getWindow());
            boolean newVal = !stage.isAlwaysOnTop();
            alwaysOnTopIcon.pseudoClassStateChanged(PseudoClass.getPseudoClass("always-on-top"), newVal);
            stage.setAlwaysOnTop(newVal);
        });
    }

    public void showSnackbar(String message, ActionType actionType) {
        Snackbar snackBar = new Snackbar(rootPane, actionType);
        snackBar.setPrefHeight(30);
        snackBar.setPrefWidth(rootPane.getWidth());
        snackBar.setAlignment(Pos.BOTTOM_CENTER);
        snackBar.show(message,4000);
    }

    public MFXDialog getBasicDialog(String title, String body) {
        return MFXDialog.builder(title, body)
                .makeScrollable()
                .buildDialogContent()
                .initWindowOwner(rootPane.getScene().getWindow())
                .initModality(Modality.APPLICATION_MODAL)
                .makeDraggable()
                .setOwnerNode(rootPane)
                .setScrimPriority(ScrimPriority.WINDOW)
                .setScrimOwner()
                .buildDialog()
                .setMaxDialogContentSize(400, 200)
                .setHeaderIcon(new MFXFontIcon("fas-circle-info", 18));
    }
}
