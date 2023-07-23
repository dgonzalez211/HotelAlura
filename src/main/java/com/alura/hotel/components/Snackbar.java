package com.alura.hotel.components;

import com.alura.hotel.enums.ActionType;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXProgressBar;
import io.github.palexdev.materialfx.enums.ButtonType;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.io.Serial;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;


public class Snackbar extends StackPane {

    private final Label toast;
    private final MFXButton actionButton;

    private final MFXProgressBar progressBar;

    private Pane snackbarContainer;
    private final BorderPane content;
    private final Group popup;
    private final ChangeListener<? super Number> sizeListener;

    private final AtomicBoolean processingQueue = new AtomicBoolean(false);
    private final ConcurrentLinkedQueue<SnackbarEvent> eventQueue = new ConcurrentLinkedQueue<>();
    private final StackPane actionContainer;

    public Snackbar() {
        this(null, null);
    }

    public Snackbar(Pane snackbarContainer, ActionType actionType) {

        BorderPane bPane = new BorderPane();

        toast = new Label();
        toast.setMinWidth(Control.USE_PREF_SIZE);
        toast.getStyleClass().add("jfx-snackbar-toast");
        toast.setWrapText(true);

        progressBar = new MFXProgressBar();
        progressBar.setMinSize(snackbarContainer.getWidth() + 20, 5);

        switch (actionType) {
            case SUCCESS -> progressBar.getStyleClass().add("mfx-progress-bar-success");
            case WARNING -> progressBar.getStyleClass().add("mfx-progress-bar-warning");
            case ERROR -> progressBar.getStyleClass().add("mfx-progress-bar-error");
            default -> progressBar.getStyleClass().add("mfx-progress-bar-info");
        }

        bPane.setBottom(progressBar);

        StackPane toastContainer = new StackPane(toast);
        toastContainer.setPadding(new Insets(20));
        bPane.setLeft(toastContainer);

        actionButton = new MFXButton("Action");
        actionButton.setButtonType(ButtonType.FLAT);
        actionButton.setMinWidth(Control.USE_PREF_SIZE);
        actionButton.getStyleClass().add("jfx-snackbar-action");

        actionContainer = new StackPane(actionButton);
        actionContainer.setPadding(new Insets(0, 30, 0, 0));
        bPane.setRight(actionContainer);

        toast.prefWidthProperty().bind(Bindings.createDoubleBinding(() -> {
            if (this.getPrefWidth() == -1) return this.getPrefWidth();
            double actionWidth = actionContainer.isVisible() ? actionContainer.getWidth() : 0.0;
            return this.prefWidthProperty().get() - actionWidth;
        }, this.prefWidthProperty(), actionContainer.widthProperty(), actionContainer.visibleProperty()));

        bPane.prefWidthProperty().bind(this.prefWidthProperty());

        content = bPane;
        content.getStyleClass().add("jfx-snackbar-content");

        popup = new Group();
        popup.getChildren().add(content);
        popup.setManaged(false);
        popup.setVisible(false);

        sizeListener = (o, oldVal, newVal) -> {
            refreshPopup();
        };

        registerSnackbarContainer(snackbarContainer);
        popup.layoutBoundsProperty().addListener((o, oldVal, newVal) -> refreshPopup());
        addEventHandler(SnackbarEvent.SNACKBAR, this::enqueue);

        this.setVisible(false);
        this.setManaged(false);
    }


    public Pane getPopupContainer() {
        return snackbarContainer;
    }


    public void registerSnackbarContainer(Pane snackbarContainer) {

        if (snackbarContainer != null) {
            if (this.snackbarContainer != null) {

                throw new IllegalArgumentException("Snackbar Container already set");
            }

            this.snackbarContainer = snackbarContainer;
            this.snackbarContainer.getChildren().add(popup);
            this.snackbarContainer.heightProperty().addListener(sizeListener);
            this.snackbarContainer.widthProperty().addListener(sizeListener);
        }

    }

    public void unregisterSnackbarContainer(Pane snackbarContainer) {

        if (snackbarContainer != null) {
            if (this.snackbarContainer == null) {
                throw new IllegalArgumentException("Snackbar Container not set");
            }

            this.snackbarContainer.getChildren().remove(popup);
            this.snackbarContainer.heightProperty().removeListener(sizeListener);
            this.snackbarContainer.widthProperty().removeListener(sizeListener);
            this.snackbarContainer = null;
        }
    }

    public void show(String toastMessage, long timeout) {
        this.show(toastMessage, null, timeout, null);
    }

    public void show(String message, String actionText, long timeout, EventHandler<? super MouseEvent> actionHandler) {
        toast.setText(message);

        if (actionText != null && !actionText.isEmpty()) {
            actionButton.setVisible(true);
            actionContainer.setVisible(true);
            actionContainer.setManaged(true);

            actionButton.setText("");
            actionButton.setText(actionText);
            actionButton.setOnMouseClicked(actionHandler);
        } else {
            actionContainer.setVisible(false);
            actionContainer.setManaged(false);
            actionButton.setVisible(false);
        }

        Timeline animation = new Timeline(
                new KeyFrame(
                        Duration.ZERO,
                        (e) -> popup.toBack(),
                        new KeyValue(popup.visibleProperty(), false, Interpolator.EASE_BOTH),
                        new KeyValue(popup.translateYProperty(), popup.getLayoutBounds().getHeight(), Interpolator.EASE_BOTH)
                ),
                new KeyFrame(
                        Duration.millis(10),
                        (e) -> popup.toFront(),
                        new KeyValue(popup.visibleProperty(), true, Interpolator.EASE_BOTH)
                ),
                new KeyFrame(Duration.millis(350),
                        new KeyValue(popup.translateYProperty(), 0, Interpolator.EASE_BOTH)
                ),
                new KeyFrame(Duration.millis((double) timeout / 2))
        );
        animation.setAutoReverse(true);
        animation.setCycleCount(2);


        animation.setOnFinished((e) -> {
            SnackbarEvent qEvent = eventQueue.poll();
            if (qEvent != null) {
                show(qEvent.getMessage(), qEvent.getActionText(), qEvent.getTimeout(), qEvent.getActionHandler());
            } else {
                processingQueue.getAndSet(false);
            }
        });

        animation.play();
    }

    public void refreshPopup() {
        Bounds contentBound = popup.getLayoutBounds();
        double offsetX = Math.ceil((snackbarContainer.getWidth() / 2)) - Math.ceil((contentBound.getWidth() / 2));
        double offsetY = snackbarContainer.getHeight() - contentBound.getHeight();
        popup.setLayoutX(offsetX);
        popup.setLayoutY(offsetY);
    }

    public void enqueue(SnackbarEvent event) {
        eventQueue.add(event);
        if (processingQueue.compareAndSet(false, true)) {
            Platform.runLater(() -> {
                SnackbarEvent qevent = eventQueue.poll();
                if (qevent != null) {
                    show(qevent.getMessage(), qevent.getActionText(), qevent.getTimeout(), qevent.getActionHandler());
                }
            });
        }
    }

    public BorderPane getContent() {
        return content;
    }

    public Label getToast() {
        return toast;
    }

    public MFXButton getActionButton() {
        return actionButton;
    }

    public StackPane getActionContainer() {
        return actionContainer;
    }

    public MFXProgressBar getProgressBar() {
        return progressBar;
    }

    public static class SnackbarEvent extends Event {

        @Serial
        private static final long serialVersionUID = 1L;

        private final String message;
        private final String actionText;
        private final long timeout;
        private final EventHandler<? super MouseEvent> actionHandler;


        public String getMessage() {
            return message;
        }

        public String getActionText() {
            return actionText;
        }

        public long getTimeout() {
            return timeout;
        }

        public EventHandler<? super MouseEvent> getActionHandler() {
            return actionHandler;
        }

        public static final EventType<SnackbarEvent> SNACKBAR = new EventType<>(Event.ANY, "SNACKBAR");

        public SnackbarEvent(String message) {
            this(message, null, 3000, null);
        }

        public SnackbarEvent(String message, String actionText, long timeout, EventHandler<? super MouseEvent> actionHandler) {
            super(SNACKBAR);
            this.message = message;
            this.actionText = actionText;
            this.timeout = timeout < 1 ? 3000 : timeout;
            this.actionHandler = actionHandler;
        }

        @Override
        public EventType<? extends SnackbarEvent> getEventType() {
            return (EventType<? extends SnackbarEvent>) super.getEventType();
        }

    }
}