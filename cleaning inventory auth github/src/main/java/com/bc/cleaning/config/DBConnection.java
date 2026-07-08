package com.bc.cleaning.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DBConnection {
    private static final String DEFAULT_URL = "jdbc:postgresql://localhost:5432/cleaning_inventory";
    private static final String DEFAULT_USER = "postgres";
    private static final String DEFAULT_PASSWORD = "postgres";

    private DBConnection() {
    }

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
            throw new ExceptionInInitializerError("PostgreSQL JDBC Driver not found: " + ex.getMessage());
        }
    }

    public static Connection getConnection() throws SQLException {
        String url = getSetting("DB_URL", DEFAULT_URL);
        String user = getSetting("DB_USER", DEFAULT_USER);
        String password = getSetting("DB_PASSWORD", DEFAULT_PASSWORD);
        return DriverManager.getConnection(url, user, password);
    }

    private static String getSetting(String key, String defaultValue) {
        String value = System.getProperty(key);
        if (value == null || value.trim().isEmpty()) {
            value = System.getenv(key);
        }
        return (value == null || value.trim().isEmpty()) ? defaultValue : value.trim();
    }
}
