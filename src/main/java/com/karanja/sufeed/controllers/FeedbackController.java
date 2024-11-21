package com.karanja.sufeed.controllers;

import com.karanja.sufeed.models.Feedback;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class FeedbackController {
    @FXML
    private TextField feedbackField;
    @FXML
    private ListView<String> feedbackListView;

    private ObservableList<String> feedbackList;

    private int selectedTopicId; // This will be set when the user selects a topic

    @FXML
    public void initialize() {
        loadFeedback();
    }

    // Load feedback into the ListView
    private void loadFeedback() {
        feedbackList = FXCollections.observableArrayList();
        for (Feedback f : Feedback.fetchByTopicId(selectedTopicId)) {
            feedbackList.add(f.getContent());
        }
        feedbackListView.setItems(feedbackList);
    }

    // Add feedback to the database and update the view
    public void addFeedback() {
        String feedbackContent = feedbackField.getText();
        if (feedbackContent.isEmpty()) {
            showAlert("Error", "Feedback cannot be empty", Alert.AlertType.ERROR);
            return;
        }

        boolean success = Feedback.save(feedbackContent, selectedTopicId);
        if (success) {
            showAlert("Success", "Feedback added successfully", Alert.AlertType.INFORMATION);
            feedbackList.add(feedbackContent);
            feedbackField.clear();
        } else {
            showAlert("Error", "Failed to add feedback", Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // This method allows setting the selectedTopicId from another controller
    public void setSelectedTopicId(int topicId) {
        this.selectedTopicId = topicId;
        loadFeedback();
    }
}
