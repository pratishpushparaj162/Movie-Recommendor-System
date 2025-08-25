package com.pratish.movies.db;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class Database {
    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) return connection;

        Properties props = new Properties();
        try (InputStream in = Database.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (in == null) {
                // fallback to example if local not present (dev convenience)
                try (InputStream inExample = Database.class.getClassLoader().getResourceAsStream("config.example.properties")) {
                    if (inExample == null) throw new RuntimeException("Missing config.properties and config.example.properties");
                    props.load(inExample);
                }
            } else {
                props.load(in);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load DB config", e);
        }

        String url = props.getProperty("db.url");
        String user = props.getProperty("db.user");
        String pass = props.getProperty("db.password");
        connection = DriverManager.getConnection(url, user, pass);
        return connection;
    }
}
