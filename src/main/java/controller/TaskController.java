package controller;

import model.Task;
import model.TaskStatus;
import service.TaskService;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

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
            System.out.println("Task added succesfully");

        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format. It should be a number");
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please use yyyy-MM-dd");
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid status or duplicate ID. " + e.getMessage());
        }
    }

    private void handleList() {

    }

    private void handleEdit() {

    }

    private void handleDelete() {

    }

    private void handleFilter() {

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
