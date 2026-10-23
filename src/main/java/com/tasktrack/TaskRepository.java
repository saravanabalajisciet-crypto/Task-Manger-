package com.tasktrack;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TaskRepository {

    public void addTask(Task task) throws SQLException {
        String sql = """
                INSERT INTO tasks
                (title, description, due_date, priority, status)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection connection = DatabaseManager.connect();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, task.getTitle());
            statement.setString(2, task.getDescription());
            statement.setString(3, task.getDueDate());
            statement.setString(4, task.getPriority());
            statement.setString(5, task.getStatus());

            statement.executeUpdate();
            System.out.println("Task added successfully!");
        }
    }

    public List<Task> getAllTasks() throws SQLException {
        List<Task> tasks = new ArrayList<>();

        String sql = """
                SELECT id, title, description, due_date, priority, status
                FROM tasks
                ORDER BY id
                """;

        try (Connection connection = DatabaseManager.connect();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                Task task = new Task(
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getString("due_date"),
                        rs.getString("priority")
                );

                task.setId(rs.getInt("id"));
                task.setStatus(rs.getString("status"));

                tasks.add(task);
            }
        }

        return tasks;
    }

    public boolean updateStatus(int id, String status)
            throws SQLException {

        String sql = "UPDATE tasks SET status = ? WHERE id = ?";

        try (Connection connection = DatabaseManager.connect();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, status);
            statement.setInt(2, id);

            return statement.executeUpdate() > 0;
        }
    }

    public boolean deleteTask(int id) throws SQLException {
        String sql = "DELETE FROM tasks WHERE id = ?";

        try (Connection connection = DatabaseManager.connect();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            return statement.executeUpdate() > 0;
        }
    }
}
