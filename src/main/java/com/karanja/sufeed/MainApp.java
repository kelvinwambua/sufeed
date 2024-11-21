package com.karanja.sufeed;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Load the registration page FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/karanja/sufeed/registration.fxml"));
            Scene scene = new Scene(loader.load(), 400, 400); // Set window size

            // Add the CSS file for styling
            scene.getStylesheets().add(getClass().getResource("/com/karanja/sufeed/styles.css").toExternalForm());

            primaryStage.setTitle("Registration - Sufeed");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
