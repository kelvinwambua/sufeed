package com.karanja.sufeed.controllers;

import com.karanja.sufeed.models.Class;
import com.karanja.sufeed.models.Topic;
import com.karanja.sufeed.models.Feedback;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class DashboardController {

    @FXML
    private TextField classNameField, topicNameField, feedbackField;
    @FXML
    private ListView<String> classListView, topicListView, feedbackListView;

    private ObservableList<String> classList;
    private ObservableList<String> topicList;
    private ObservableList<String> feedbackList;

    private int selectedClassId = -1; // Stores the selected class ID
    private int selectedTopicId = -1; // Stores the selected topic ID

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

        // Clear dependent lists
        topicListView.getItems().clear();
        feedbackListView.getItems().clear();
    }

    // Load topics when a class is selected
    private void loadTopics(int classId) {
        topicList = FXCollections.observableArrayList();
        for (Topic t : Topic.fetchByClassId(classId)) {
            topicList.add(t.getName());
        }
        topicListView.setItems(topicList);

        // Clear feedback list
        feedbackListView.getItems().clear();
    }

    // Load feedback when a topic is selected
    private void loadFeedback(int topicId) {
        feedbackList = FXCollections.observableArrayList();
        for (Feedback f : Feedback.fetchByTopicId(topicId)) {
            feedbackList.add(f.getContent());
        }
        feedbackListView.setItems(feedbackList);
    }

    // Add a new class
    public void addClass() {
        String className = classNameField.getText();
        if (className.isEmpty()) {
            showAlert("Error", "Class name cannot be empty", Alert.AlertType.ERROR);
            return;
        }

        if (Class.save(className)) {
            classList.add(className);
            classNameField.clear();
            showAlert("Success", "Class added successfully!", Alert.AlertType.INFORMATION);
        } else {
            showAlert("Error", "Failed to add class", Alert.AlertType.ERROR);
        }
    }

    // Add a new topic
    public void addTopic() {
        if (selectedClassId == -1) {
            showAlert("Error", "Please select a class before adding a topic", Alert.AlertType.ERROR);
            return;
        }

        String topicName = topicNameField.getText();
        if (topicName.isEmpty()) {
            showAlert("Error", "Topic name cannot be empty", Alert.AlertType.ERROR);
            return;
        }

        if (Topic.save(topicName, selectedClassId)) {
            topicList.add(topicName);
            topicNameField.clear();
            showAlert("Success", "Topic added successfully!", Alert.AlertType.INFORMATION);
        } else {
            showAlert("Error", "Failed to add topic", Alert.AlertType.ERROR);
        }
    }

    // Add new feedback
    public void addFeedback() {
        if (selectedTopicId == -1) {
            showAlert("Error", "Please select a topic before adding feedback", Alert.AlertType.ERROR);
            return;
        }

        String feedbackContent = feedbackField.getText();
        if (feedbackContent.isEmpty()) {
            showAlert("Error", "Feedback cannot be empty", Alert.AlertType.ERROR);
            return;
        }

        if (Feedback.save(feedbackContent, selectedTopicId)) {
            feedbackList.add(feedbackContent);
            feedbackField.clear();
            showAlert("Success", "Feedback added successfully!", Alert.AlertType.INFORMATION);
        } else {
            showAlert("Error", "Failed to add feedback", Alert.AlertType.ERROR);
        }
    }

    // Set listeners for class and topic selection
    @FXML
    public void handleClassSelection() {
        String selectedClass = classListView.getSelectionModel().getSelectedItem();
        if (selectedClass != null) {
            selectedClassId = Class.fetchAll().stream()
                .filter(c -> c.getName().equals(selectedClass))
                .findFirst()
                .map(Class::getId)
                .orElse(-1);
            loadTopics(selectedClassId);
        }
    }

    @FXML
    public void handleTopicSelection() {
        String selectedTopic = topicListView.getSelectionModel().getSelectedItem();
        if (selectedTopic != null) {
            selectedTopicId = Topic.fetchByClassId(selectedClassId).stream()
                .filter(t -> t.getName().equals(selectedTopic))
                .findFirst()
                .map(Topic::getId)
                .orElse(-1);
            loadFeedback(selectedTopicId);
        }
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
