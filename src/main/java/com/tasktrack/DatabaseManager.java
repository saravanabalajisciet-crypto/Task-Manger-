package com.tasktrack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {

    private static final String DATABASE_URL = "jdbc:sqlite:tasktrack.db";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL);
    }

    public static void initializeDatabase() {
        String sql = """
                CREATE TABLE IF NOT EXISTS tasks (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    title TEXT NOT NULL,
                    description TEXT,
                    due_date TEXT,
                    priority TEXT NOT NULL DEFAULT 'MEDIUM',
                    status TEXT NOT NULL DEFAULT 'PENDING'
                );
                """;

        try (Connection connection = connect();
             Statement statement = connection.createStatement()) {

            statement.execute(sql);
            System.out.println("TaskTrack database initialized successfully!");

        } catch (SQLException e) {
            System.err.println("Database initialization failed: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        initializeDatabase();
    }
}
