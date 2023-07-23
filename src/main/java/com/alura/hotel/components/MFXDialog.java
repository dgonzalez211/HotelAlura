package com.alura.hotel.components;

import com.alura.hotel.enums.ActionType;
import com.alura.hotel.exceptions.InvalidDialogException;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialog;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialogBuilder;
import io.github.palexdev.materialfx.dialogs.MFXStageDialog;
import io.github.palexdev.materialfx.dialogs.MFXStageDialogBuilder;
import io.github.palexdev.materialfx.enums.ScrimPriority;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Window;

import java.util.Map;

public class MFXDialog {

    private final String title;
    private final String body;
    private final MFXGenericDialogBuilder mfxGenericDialogBuilder;
    private MFXStageDialogBuilder mfxStageDialogBuilder;

    private MFXGenericDialog mfxGenericDialog;
    private MFXStageDialog mfxStageDialog;

    public static MFXDialog builder(String title, String body) {
        return new MFXDialog(title, body);
    }

    private MFXDialog(String title, String body) {
        mfxGenericDialogBuilder = MFXGenericDialogBuilder.build();
        mfxStageDialogBuilder = MFXStageDialogBuilder.build();

        this.title = title;
        this.body = body;
    }

    public void show() {
        mfxStageDialog.show();
    }

    public MFXDialog close() {
        mfxStageDialog.close();
        return this;
    }

    public MFXDialog clearActions() {
        mfxGenericDialog.clearActions();
        return this;
    }

    public MFXDialog buildDialogContent() {
        mfxGenericDialog = mfxGenericDialogBuilder.get();
        mfxStageDialogBuilder = MFXGenericDialogBuilder.build(mfxGenericDialog).toStageDialogBuilder();
        return this;
    }

    public MFXDialog buildDialog() {
        mfxStageDialog = mfxStageDialogBuilder.get();

        mfxGenericDialog.setHeaderText(title);
        mfxGenericDialog.setContentText(body);

        return this;
    }

    public MFXDialog setContentText(String contentText) {
        validateBuild();
        mfxGenericDialog.setContentText(contentText);
        return this;
    }

    public MFXDialog setHeaderText(String headerText) {
        validateBuild();
        mfxGenericDialog.setHeaderText(headerText);
        return this;
    }

    public MFXDialog setHeaderIcon(Node headerIcon) {
        validateBuild();
        mfxGenericDialog.setHeaderIcon(headerIcon);
        return this;
    }

    public MFXDialog setMaxDialogContentSize(int width, int height) {
        validateBuild();
        mfxGenericDialog.setMaxSize(width, height);
        return this;
    }

    public void addActionButton(MFXButton button, ActionType actionType, EventHandler<MouseEvent> action) {
        validateBuild();
        mfxGenericDialog.addActions(Map.entry(button, action));
    }

    public MFXDialog makeScrollable() {
        mfxGenericDialogBuilder.makeScrollable(true);
        return this;
    }

    public MFXDialog setTitle(String title) {
        mfxStageDialogBuilder.setTitle(title);
        return this;
    }

    public MFXDialog makeDraggable() {
        mfxStageDialogBuilder.setDraggable(true);
        return this;
    }

    public MFXDialog initWindowOwner(Window window) {
        mfxStageDialogBuilder.initOwner(window);
        return this;
    }

    public MFXDialog setOwnerNode(Pane pane) {
        mfxStageDialogBuilder.setOwnerNode(pane);
        return this;
    }

    public MFXDialog setScrimPriority(ScrimPriority priority) {
        mfxStageDialogBuilder.setScrimPriority(priority);
        return this;
    }

    public MFXDialog initModality(Modality modality) {
        mfxStageDialogBuilder.initModality(modality);
        return this;
    }

    public MFXDialog setScrimOwner() {
        mfxStageDialogBuilder.setScrimOwner(true);
        return this;
    }

    public MFXGenericDialog getMfxGenericDialog() {
        return mfxGenericDialog;
    }

    public MFXStageDialog getMfxStageDialog() {
        return mfxStageDialog;
    }

    public void validateBuild() {
        if (mfxGenericDialog == null || mfxStageDialog == null) {
            throw new InvalidDialogException("Please call the buildAll() method before adding window properties!");
        }
    }
}
