package com.karanja.sufeed.controllers;

import com.karanja.sufeed.models.User;
import com.karanja.sufeed.utils.PasswordUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class RegistrationController {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    public void register() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String hashedPassword = PasswordUtil.hashPassword(password);

        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Please fill out all fields.", Alert.AlertType.ERROR);
            return;
        }

        if (User.findByUsername(username) != null) {
            showAlert("Error", "Username already exists!", Alert.AlertType.ERROR);
            return;
        }

        boolean success = User.save(username, hashedPassword);

        if (success) {
            showAlert("Success", "Registration successful!", Alert.AlertType.INFORMATION);
            navigateToLogin(); // Redirect to login page
        } else {
            showAlert("Error", "Registration failed!", Alert.AlertType.ERROR);
        }
    }

    public void navigateToLogin() {
        try {
            Stage stage = (Stage) usernameField.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/karanja/sufeed/login.fxml"));
            Scene scene = new Scene(loader.load());
            scene.getStylesheets().add(getClass().getResource("/com/karanja/sufeed/styles.css").toExternalForm());
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
