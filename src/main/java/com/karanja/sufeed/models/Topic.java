package com.karanja.sufeed.models;

import com.karanja.sufeed.utils.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Topic {
    private int id;
    private String name;
    private int classId;

    public Topic(int id, String name, int classId) {
        this.id = id;
        this.name = name;
        this.classId = classId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getClassId() {
        return classId;
    }

    // Fetch topics by class ID
    public static List<Topic> fetchByClassId(int classId) {
        List<Topic> topics = new ArrayList<>();
        try (Connection conn = Database.connect()) {
            String query = "SELECT * FROM topics WHERE class_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, classId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                topics.add(new Topic(rs.getInt("id"), rs.getString("name"), classId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return topics;
    }

    // Save a topic to the database
    public static boolean save(String name, int classId) {
        try (Connection conn = Database.connect()) {
            String query = "INSERT INTO topics (name, class_id) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, name);
            stmt.setInt(2, classId);
            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
