package com.karanja.sufeed.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static final String URL = "jdbc:postgresql://localhost:5432/db_Joy_Karanga_Adm";
    private static final String USER = "postgres";
    private static final String PASSWORD = "King_kelvin1";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
