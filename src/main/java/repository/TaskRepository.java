package repository;

import model.Task;

import java.util.List;

public interface TaskRepository {
    void addTask(Task task);
    Task getTaskById(int id);
    void updateTask(Task task);
    void deleteTask(int id);
    List<Task> getAllTasks();
}
