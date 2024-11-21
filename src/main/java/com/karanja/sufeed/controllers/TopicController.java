package com.karanja.sufeed.controllers;

import com.karanja.sufeed.models.Topic;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class TopicController {
    @FXML
    private TextField topicNameField;
    @FXML
    private ListView<String> topicListView;

    private ObservableList<String> topicList;

    private int selectedClassId; // Set this based on the selected class in UI

    @FXML
    public void initialize() {
        loadTopics();
    }

    // Load topics into the ListView
    private void loadTopics() {
        topicList = FXCollections.observableArrayList();
        for (Topic t : Topic.fetchByClassId(selectedClassId)) {
            topicList.add(t.getName());
        }
        topicListView.setItems(topicList);
    }

    // Add a topic to the database and update the view
    public void addTopic() {
        String topicName = topicNameField.getText();
        if (topicName.isEmpty()) {
            showAlert("Error", "Topic name cannot be empty", Alert.AlertType.ERROR);
            return;
        }

        boolean success = Topic.save(topicName, selectedClassId);
        if (success) {
            showAlert("Success", "Topic added successfully", Alert.AlertType.INFORMATION);
            topicList.add(topicName);
            topicNameField.clear();
        } else {
            showAlert("Error", "Failed to add topic", Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
