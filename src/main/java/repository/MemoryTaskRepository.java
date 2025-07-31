package repository;

import model.Task;

import java.util.*;

public class MemoryTaskRepository implements TaskRepository {
    // Хранилище задач: ключ — ID задачи, значение — сама задача
    private final Map<Integer, Task> tasks = new HashMap<>();

    public void addTask(Task task) {
        validateTask(task);
        if (tasks.containsKey(task.getId())) {
            throw new IllegalArgumentException("Задача с таким ID уже существует");
        }
        tasks.put(task.getId(), task);
    }

    public Task getTaskById(int id) {
        validateId(id);
        ensureTaskExists(id);
        return tasks.get(id);
    }

    public void updateTask(Task task) {
        validateTask(task);
        ensureTaskExists(task.getId());

        tasks.replace(task.getId(), task);
    }

    public void deleteTask(int id) {
        validateId(id);
        ensureTaskExists(id);
        tasks.remove(id);
    }

    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());//возврат копии значений, не трогая мапу
    }

    //валидация поступающих данных task
    private void validateTask(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Task не может быть null");
        }
        if (task.getTitle().isBlank() || task.getId() <= 0) {
            throw new IllegalArgumentException("Заданы некорректные данные");
        }
    }

    //валидация id
    private void validateId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Значение ID долэно быть больше нуля");
        }
    }

    //проверка наличия объекта в хранилище
    private void ensureTaskExists(int id) {
        if (!tasks.containsKey(id)) {
            throw new NoSuchElementException("Задача с таким ID не найдена");
        }
    }
}
