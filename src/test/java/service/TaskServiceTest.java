package service;

import model.Task;
import model.TaskStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.TaskRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskServiceTest {
    private TaskRepository repository;
    private TaskService service;

    @BeforeEach
    void setUp() {
        repository = mock(TaskRepository.class);
        service = new TaskServiceImpl(repository);
    }

    @Test
    @DisplayName("Успешное создание задачи")
    void createTask_shouldCallRepositoryAddTask() {
        Task task = new Task(1, "Test", "Desc", LocalDate.now(), TaskStatus.TODO);

        service.createTask(task);

        verify(repository, times(1)).addTask(task);
    }

    @Test
    @DisplayName("Успешное обновление задачи")
    void updateTask_shouldCallRepositoryUpdateTask() {
        Task task = new Task(2, "Updated", "Desc", LocalDate.now(), TaskStatus.IN_PROGRESS);

        service.updateTask(task);

        verify(repository, times(1)).updateTask(task);
    }

    @Test
    @DisplayName("Успешное удаление задачи")
    void deleteTask_shouldCallRepositoryDeleteTask() {
        int taskId = 3;

        service.deleteTask(taskId);

        verify(repository, times(1)).deleteTask(taskId);
    }

    @Test
    @DisplayName("Фильтрация по статусу возвращает только нужные задачи")
    void filterTasksByStatus_shouldReturnFilteredList() {
        Task task1 = new Task(1, "T1", "D1", LocalDate.now(), TaskStatus.TODO);
        Task task2 = new Task(2, "T2", "D2", LocalDate.now(), TaskStatus.DONE);
        Task task3 = new Task(3, "T3", "D3", LocalDate.now(), TaskStatus.TODO);

        List<Task> allTasks = Arrays.asList(task1, task2, task3);

        when(repository.getAllTasks()).thenReturn(allTasks);

        List<Task> filtered = service.filterTasksByStatus(TaskStatus.TODO);

        assertEquals(2, filtered.size());
        assertTrue(filtered.contains(task1));
        assertTrue(filtered.contains(task3));
        assertFalse(filtered.contains(task2));
    }

    @Test
    @DisplayName("Успешная сортировка задач по дате")
    void getAllTasksSortedByDueDate_shouldReturnSortedList() {
        Task task1 = new Task(1, "T1", "D1", LocalDate.of(2025, 8, 10), TaskStatus.TODO);
        Task task2 = new Task(2, "T2", "D2", LocalDate.of(2025, 7, 1), TaskStatus.DONE);
        Task task3 = new Task(3, "T3", "D3", LocalDate.of(2025, 8, 1), TaskStatus.IN_PROGRESS);

        List<Task> allTasks = List.of(task1, task2, task3);

        when(repository.getAllTasks()).thenReturn(allTasks);

        List<Task> sorted = service.getAllTasksSortedByDueDate();

        assertEquals(List.of(task2, task3, task1), sorted);
    }

    @Test
    @DisplayName("Успешная сортировка задач по статусу")
    void getAllTasksSortedByStatus_shouldReturnSortedList() {
        Task task1 = new Task(1, "T1", "D1", LocalDate.now(), TaskStatus.DONE);
        Task task2 = new Task(2, "T2", "D2", LocalDate.now(), TaskStatus.TODO);
        Task task3 = new Task(3, "T3", "D3", LocalDate.now(), TaskStatus.IN_PROGRESS);

        List<Task> allTasks = List.of(task1, task2, task3);

        when(repository.getAllTasks()).thenReturn(allTasks);

        List<Task> sorted = service.getAllTasksSortedByStatus();

        assertEquals(List.of(task2, task3, task1), sorted);
    }
}
