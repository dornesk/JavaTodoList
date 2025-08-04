package service;

import model.Task;
import model.TaskStatus;
import repository.TaskRepository;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TaskServiceImpl implements TaskService {
    private final TaskRepository repository;
    public TaskServiceImpl(TaskRepository repository) {
        this.repository = repository;
    }

    @Override
    public void createTask(Task task) {
        if (task == null) {
            throw new NullPointerException("Task cannot be null");
        }
        if (task.getDueDate() == null) {
            throw new IllegalArgumentException("Due date cannot be null");
        }
        if (task.getDueDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Due date cannot be in the past");
        }
        repository.addTask(task);
    }

    @Override
    public void updateTask(Task task) {
        if (task == null) {
            throw new NullPointerException("Task cannot be null");
        }
        if (task.getDueDate() == null) {
            throw new IllegalArgumentException("Due date cannot be null");
        }
        if (task.getDueDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Due date cannot be in the past");
        }
        repository.updateTask(task);
    }

    @Override
    public void deleteTask(int id) {
        repository.deleteTask(id);
    }

    @Override
    public List<Task> getAllTasks() {
        return repository.getAllTasks();
    }

    @Override
    public Task getTaskById(int id) {
        return repository.getTaskById(id);
    }

    @Override
    public List<Task> filterTasksByStatus(TaskStatus status) {
        if (status == null) {
            throw new NullPointerException("Task cannot be null");
        }
        return repository.getAllTasks().stream()
                .filter(task -> task.getStatus() == status)
                .collect(Collectors.toList());
    }

    @Override
    public List<Task> getAllTasksSortedByDueDate() {
        return repository.getAllTasks().stream()
                .sorted(Comparator.comparing(Task::getDueDate))
                .collect(Collectors.toList());
    }

    @Override
    public List<Task> getAllTasksSortedByStatus() {
        return repository.getAllTasks().stream()
                .sorted(Comparator.comparing(Task::getStatus))
                .collect(Collectors.toList());
    }
}
