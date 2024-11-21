package com.karanja.sufeed.controllers;

import com.karanja.sufeed.models.Class;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class ClassController {
    @FXML
    private TextField classNameField;
    @FXML
    private ListView<String> classListView;

    private ObservableList<String> classList;

    @FXML
    public void initialize() {
        loadClasses();
    }

    // Load classes into the ListView
    private void loadClasses() {
        classList = FXCollections.observableArrayList();
        for (Class c : Class.fetchAll()) {
            classList.add(c.getName());
        }
        classListView.setItems(classList);
    }

    // Add a class to the database and update the view
    public void addClass() {
        String className = classNameField.getText();
        if (className.isEmpty()) {
            showAlert("Error", "Class name cannot be empty", Alert.AlertType.ERROR);
            return;
        }

        boolean success = Class.save(className);
        if (success) {
            showAlert("Success", "Class added successfully", Alert.AlertType.INFORMATION);
            classList.add(className);
            classNameField.clear();
        } else {
            showAlert("Error", "Failed to add class", Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
