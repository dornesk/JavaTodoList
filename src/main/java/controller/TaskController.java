package controller;

import service.TaskService;

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

                String choice = reader.readLine().toLowerCase();

                switch (choice) {
                    case "add" -> handleAdd();
                    case "list" -> handleList();
                    case "edit" -> handleEdit();
                    case "delete" -> handleDelete();
                    case "filter" -> handleFilter();
                    case "sort" -> handleSort();
                    case "exit" -> running = false;
                    default -> System.out.println("Invalid choice");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void handleAdd() {

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
}
