package repository;

import model.Task;

import java.util.List;

public interface TaskRepository {
    boolean addTask(Task task);
    Task getTaskById(int id);
    boolean updateTask(Task task);
    boolean deleteTask(int id);
    List<Task> getAllTasks();
}
