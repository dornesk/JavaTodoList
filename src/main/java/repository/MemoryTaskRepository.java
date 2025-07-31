package repository;

import model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemoryTaskRepository implements TaskRepository {
    // Хранилище задач: ключ — ID задачи, значение — сама задача
    private final Map<Integer, Task> tasks = new HashMap<>();

    public boolean addTask(Task task) {
        return false;
    }

    public Task getTaskById(int id) {
        return null;
    }

    public boolean updateTask(Task task) {
        return false;
    }

    public boolean deleteTask(int id){
        return false;
    }

    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());//возврат копии значений, не трогая мапу
    }
}
