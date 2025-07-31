package service;

import model.Task;
import model.TaskStatus;

import java.util.List;

public interface TaskService {
    void createTask(Task task);
    void updateTask(Task task);
    void deleteTask(int id);
    List<Task> getAllTasks();
    Task getTaskById(int id);
    List<Task> filterTasksByStatus(TaskStatus status);
}
