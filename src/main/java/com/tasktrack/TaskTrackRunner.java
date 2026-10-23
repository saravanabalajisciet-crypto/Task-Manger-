package com.tasktrack;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class TaskTrackRunner {

    public static void main(String[] args) {

        DatabaseManager.initializeDatabase();

        TaskRepository repository = new TaskRepository();

        try (Scanner scanner = new Scanner(System.in)) {

            while (true) {
                System.out.println("\n===== TASKTRACK =====");
                System.out.println("1. Add Task");
                System.out.println("2. View All Tasks");
                System.out.println("3. Mark Task Completed");
                System.out.println("4. Delete Task");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");

                String choice = scanner.nextLine().trim();

                try {
                    switch (choice) {
                        case "1" -> {
                            System.out.print("Enter title: ");
                            String title = scanner.nextLine().trim();

                            if (title.isEmpty()) {
                                System.out.println("Title cannot be empty.");
                                break;
                            }

                            System.out.print("Enter description: ");
                            String description = scanner.nextLine();

                            System.out.print("Enter due date (YYYY-MM-DD): ");
                            String dueDate = scanner.nextLine().trim();

                            System.out.print("Enter priority (LOW/MEDIUM/HIGH): ");
                            String priority = scanner.nextLine()
                                    .trim().toUpperCase();

                            if (!priority.equals("LOW")
                                    && !priority.equals("MEDIUM")
                                    && !priority.equals("HIGH")) {
                                System.out.println("Invalid priority.");
                                break;
                            }

                            Task task = new Task(
                                    title, description, dueDate, priority
                            );

                            repository.addTask(task);
                        }

                        case "2" -> {
                            List<Task> tasks = repository.getAllTasks();

                            System.out.println("\n--- Your Tasks ---");

                            if (tasks.isEmpty()) {
                                System.out.println("No tasks found.");
                            }

                            for (Task task : tasks) {
                                System.out.println(
                                        "ID: " + task.getId()
                                        + " | Title: " + task.getTitle()
                                        + " | Due: " + task.getDueDate()
                                        + " | Priority: " + task.getPriority()
                                        + " | Status: " + task.getStatus()
                                );
                            }
                        }

                        case "3" -> {
                            System.out.print("Enter task ID: ");
                            int id = Integer.parseInt(scanner.nextLine().trim());

                            boolean updated = repository.updateStatus(
                                    id, "COMPLETED"
                            );

                            System.out.println(updated
                                    ? "Task marked as completed!"
                                    : "Task ID not found.");
                        }

                        case "4" -> {
                            System.out.print("Enter task ID to delete: ");
                            int id = Integer.parseInt(scanner.nextLine().trim());

                            boolean deleted = repository.deleteTask(id);

                            System.out.println(deleted
                                    ? "Task deleted successfully!"
                                    : "Task ID not found.");
                        }

                        case "5" -> {
                            System.out.println("TaskTrack closed. Bye!");
                            return;
                        }

                        default ->
                                System.out.println("Invalid choice. Enter 1-5.");
                    }

                } catch (NumberFormatException e) {
                    System.out.println("Please enter a valid numeric ID.");
                } catch (SQLException e) {
                    System.out.println("Database error: " + e.getMessage());
                }
            }

        }
    }
}
