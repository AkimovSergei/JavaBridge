package com.sa.bridge.controllers;

import com.sa.bridge.services.Dispatcher;
import com.sa.bridge.services.events.EventHandler;
import com.sa.bridge.sides.Side;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Created by sergeiakimov
 */
public class Controller implements Initializable {
    private final Integer INITIAL_CARS_AMOUNT = 15;

    private Dispatcher dispatcher;

    @FXML
    private Circle lightLeft;
    @FXML
    private Circle lightRight;
    @FXML
    private ImageView imgCar;
    @FXML
    private ImageView imgArrowRight;
    @FXML
    private ImageView imgArrowLeft;
    @FXML
    private Label labelCarsAmountLeft;
    @FXML
    private Label labelCarsAmountRight;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // activate dispatcher
        Map<Side, Integer> initialCarNumbers = new HashMap<>();
        initialCarNumbers.put(Side.LEFT, INITIAL_CARS_AMOUNT);
        initialCarNumbers.put(Side.RIGHT, INITIAL_CARS_AMOUNT);
        this.dispatcher = new Dispatcher(initialCarNumbers, onCarsAmountChangedEvent());

        // labels
        labelCarsAmountRight.setText(INITIAL_CARS_AMOUNT.toString());
        labelCarsAmountLeft.setText(INITIAL_CARS_AMOUNT.toString());

        // side init
        switchSidesTo(Side.randomSide());
    }


    public void onSwitchClick(MouseEvent mouseEvent) {
        if (dispatcher.getActiveSite().equals(Side.RIGHT)) {
            switchSidesTo(Side.LEFT);
        } else {
            switchSidesTo(Side.RIGHT);
        }
    }

    public void onAddTenLeftClick(MouseEvent mouseEvent) {
        dispatcher.addCarsToSide(Side.LEFT, INITIAL_CARS_AMOUNT);
    }

    public void onAddTenRightClick(MouseEvent mouseEvent) {
        dispatcher.addCarsToSide(Side.RIGHT, INITIAL_CARS_AMOUNT);
    }

    private void switchSidesTo(Side activeSide) {
        if (activeSide.equals(Side.RIGHT)) {
            imgCar.setVisible(true);
            imgArrowRight.setVisible(true);
            imgArrowLeft.setVisible(true);

            imgCar.rotateProperty().setValue(0);
            imgArrowRight.rotateProperty().setValue(180);
            imgArrowLeft.rotateProperty().setValue(180);

            lightLeft.setFill(Color.RED);
            lightRight.setFill(Color.GREEN);

            dispatcher.setActiveSite(Side.RIGHT);
        } else if (activeSide.equals(Side.LEFT)) {
            imgCar.setVisible(true);
            imgArrowRight.setVisible(true);
            imgArrowLeft.setVisible(true);

            imgCar.rotateProperty().setValue(180);
            imgArrowRight.rotateProperty().setValue(0);
            imgArrowLeft.rotateProperty().setValue(0);

            lightLeft.setFill(Color.GREEN);
            lightRight.setFill(Color.RED);

            dispatcher.setActiveSite(Side.LEFT);
        } else {
            imgCar.setVisible(false);
            imgArrowRight.setVisible(false);
            imgArrowLeft.setVisible(false);

            lightLeft.setFill(Color.RED);
            lightRight.setFill(Color.RED);

            dispatcher.setActiveSite(Side.NONE);
        }
    }

    private EventHandler<Integer> onCarsAmountChangedEvent() {
        return (side, count) -> {
            if (side.equals(Side.LEFT)) {
                Platform.runLater(() -> {
                    labelCarsAmountLeft.setText(count.toString());
                    if (count == 0) {
                        switchSidesTo(Side.RIGHT);
                    }
                });
            } else if (side.equals(Side.RIGHT)) {
                Platform.runLater(() -> {
                    labelCarsAmountRight.setText(count.toString());
                    if (count == 0) {
                        switchSidesTo(Side.LEFT);
                    }
                });
            }
        };
    }


}
