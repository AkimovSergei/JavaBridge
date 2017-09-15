package com.sa.bridge;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class BridgeApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        URL rootWindow = getClass().getClassLoader().getResource("main.fxml");
        if (rootWindow != null) {
            Parent root = FXMLLoader.load(rootWindow);
            primaryStage.setTitle("Bridge");
            primaryStage.setScene(new Scene(root, 640, 360));
            primaryStage.show();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
