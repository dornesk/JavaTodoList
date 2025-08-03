package service;

import model.Task;
import model.TaskStatus;
import repository.TaskRepository;

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
        repository.addTask(task);
    }

    @Override
    public void updateTask(Task task) {
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
