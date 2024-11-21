package com.karanja.sufeed.controllers;

import com.karanja.sufeed.utils.PasswordUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import com.karanja.sufeed.models.User;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    public void login() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        User user = User.findByUsername(username);

        if (user != null && PasswordUtil.hashPassword(password).equals(user.getPasswordHash())) {
            showAlert("Success", "Login successful!", Alert.AlertType.INFORMATION);
            loadMainView(); // Navigate to the dashboard
        } else {
            showAlert("Error", "Invalid credentials!", Alert.AlertType.ERROR);
        }
    }

    public void navigateToSignUp() {
        try {
            AnchorPane signUpView = FXMLLoader.load(getClass().getResource("/com/karanja/sufeed/registration.fxml"));
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.getScene().setRoot(signUpView);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Could not load the Sign-Up page.", Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void loadMainView() {
        try {

            AnchorPane signUpView = FXMLLoader.load(getClass().getResource("/com/karanja/sufeed/dashboard.fxml"));
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.getScene().setRoot(signUpView);
            stage.setMaximized(true);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Could not load the main dashboard.", Alert.AlertType.ERROR);
        }
    }
}
