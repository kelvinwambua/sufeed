package com.karanja.sufeed.models;

import com.karanja.sufeed.utils.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Feedback {
    private int id;
    private String content;
    private int topicId;

    public Feedback(int id, String content, int topicId) {
        this.id = id;
        this.content = content;
        this.topicId = topicId;
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public int getTopicId() {
        return topicId;
    }

    // Fetch feedback for a specific topic
    public static List<Feedback> fetchByTopicId(int topicId) {
        List<Feedback> feedbackList = new ArrayList<>();
        try (Connection conn = Database.connect()) {
            String query = "SELECT * FROM feedback WHERE topic_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, topicId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                feedbackList.add(new Feedback(rs.getInt("id"), rs.getString("content"), topicId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return feedbackList;
    }

    // Save feedback to the database
    public static boolean save(String content, int topicId) {
        try (Connection conn = Database.connect()) {
            String query = "INSERT INTO feedback (content, topic_id) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, content);
            stmt.setInt(2, topicId);
            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
