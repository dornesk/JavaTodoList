package controller;

import model.Task;
import model.TaskStatus;
import service.TaskService;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class TaskController {
    private final TaskService service;
    private final InputReader reader;

    public TaskController(TaskService service, InputReader reader) {
        this.service = service;
        this.reader = reader;
    }

    public void run() {
        boolean running = true;

        while (running) {
            try {
                System.out.println("""
                        \n
                        Write 'add' to add Task
                        Write 'list' to see all Tasks
                        Write 'edit' to edit existing Task
                        Write 'delete' to delete Task
                        Write 'filter' to filter Tasks with Status
                        Write 'sort' to sort all Tasks
                        Write 'exit' to Exit
                        """);

                String choice = reader.readLine().toUpperCase();

                switch (Commands.valueOf(choice)) {
                    case ADD -> handleAdd();
                    case LIST -> handleList();
                    case EDIT -> handleEdit();
                    case DELETE -> handleDelete();
                    case FILTER -> handleFilter();
                    case SORT -> handleSort();
                    case EXIT -> running = false;
                    default -> System.out.println("Invalid choice");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void handleAdd() {
        int id = promptInt("Enter ID (number): ");
        String title = promptNotBlank("Enter title: ");
        String description = promptNotBlank("Enter description: ");
        LocalDate dueDate = promptDate("Enter due date (yyyy-MM-dd): ");
        TaskStatus status = promptStatus("Enter status (TODO, IN_PROGRESS, DONE): ");

        try {
            Task task = new Task(id, title, description, dueDate, status);
            service.createTask(task);
            System.out.println("Task added successfully");

        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format. It should be a number");
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please use yyyy-MM-dd");
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid status or duplicate ID. " + e.getMessage());
        }
    }

    private void handleList() {
        try {
            List<Task> tasks = service.getAllTasks();
            if (tasks.isEmpty()) {
                System.out.println("No tasks found");
            } else {
                System.out.println("Tasks list: ");
                tasks.forEach(task -> System.out.printf(
                        "ID: %d, Title: %s, Description: %s, Due Date: %s, Status: %s%n",
                        task.getId(),
                        task.getTitle(),
                        task.getDescription(),
                        task.getDueDate(),
                        task.getStatus()
                ));
            }
        } catch (Exception e) {
            System.out.println("Error while listing tasks: " + e.getMessage());
        }
    }

    private void handleEdit() {
        try {
            int id = promptInt("Enter ID of the task to edit: ");
            Task existingTask = service.getTaskById(id);

            if (existingTask == null) {
                System.out.println("Task not found with ID: " + id);
            } else {
                String newTitle = promptNotBlank("Enter new title: ");
                String newDescription = promptNotBlank("Enter new description: ");
                LocalDate newDueDate = promptDate("Enter new due date (yyyy-MM-dd): ");
                TaskStatus newStatus = promptStatus("Enter new status (TODO, IN_PROGRESS, DONE): ");

                Task updatedTask = new Task(id, newTitle, newDescription, newDueDate, newStatus);
                service.updateTask(updatedTask);
                System.out.println("Task updated successfully.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format.");
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please use yyyy-MM-dd.");
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid status or data. " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error while editing task: " + e.getMessage());
        }
    }

    private void handleDelete() {
        try {
            int id = promptInt("Enter ID of the task to delete: ");
            Task existingTask = service.getTaskById(id);

            if (existingTask == null) {
                System.out.println("Task not found with ID: " + id);
            } else {
                service.deleteTask(id);
                System.out.println("Task deleted successfully.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format.");
        } catch (Exception e) {
            System.out.println("Error while deleting task: " + e.getMessage());
        }
    }

    private void handleFilter() {
        try {
            TaskStatus status = promptStatus("Enter status to filter (TODO, IN_PROGRESS, DONE): ");
            var filteredTasks = service.filterTasksByStatus(status);

            if (filteredTasks.isEmpty()) {
                System.out.println("No tasks found with status: " + status);
            } else {
                System.out.println("Tasks with status " + status + ":");
                filteredTasks.forEach(System.out::println);
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid status. Please enter TODO, IN_PROGRESS, or DONE.");
        } catch (Exception e) {
            System.out.println("Error while filtering tasks: " + e.getMessage());
        }
    }

    private void handleSort() {

    }

    private String promptNotBlank(String prompt) {
        while (true) {
            System.out.println(prompt);
            String input = reader.readLine();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("Input cannot be empty. Please try again");
        }
    }

    private LocalDate promptDate(String prompt) {
        while (true) {
            System.out.println(prompt);
            String input = reader.readLine();
            try {
                return LocalDate.parse(input);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use YYYY-MM-DD.");
            }
        }
    }

    private int promptInt(String prompt) {
        while (true) {
            System.out.println(prompt);
            String input = reader.readLine();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please try again.");
            }
        }
    }

    private TaskStatus promptStatus(String prompt) {
        while (true) {
            System.out.println(prompt);
            String input = reader.readLine().toUpperCase();
            try {
                return TaskStatus.valueOf(input);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid status. Please enter TODO, IN_PROGRESS, or DONE.");
            }
        }
    }
}
