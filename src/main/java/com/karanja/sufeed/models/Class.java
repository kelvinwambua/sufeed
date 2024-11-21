package com.karanja.sufeed.models;

import com.karanja.sufeed.utils.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Class {
    private int id;
    private String name;

    public Class(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    // Fetch all classes from the database
    public static List<Class> fetchAll() {
        List<Class> classes = new ArrayList<>();
        try (Connection conn = Database.connect()) {
            String query = "SELECT * FROM classes";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                classes.add(new Class(rs.getInt("id"), rs.getString("name")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return classes;
    }

    // Save a class to the database
    public static boolean save(String name) {
        try (Connection conn = Database.connect()) {
            String query = "INSERT INTO classes (name) VALUES (?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, name);
            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
