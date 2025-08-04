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
    @DisplayName("Создание задачи с null выбрасывает NullPointerException")
    void createTask_withNull_shouldThrowException() {
        assertThrows(NullPointerException.class, () -> service.createTask(null));
    }

    @Test
    @DisplayName("Успешное обновление задачи")
    void updateTask_shouldCallRepositoryUpdateTask() {
        Task task = new Task(2, "Updated", "Desc", LocalDate.now(), TaskStatus.IN_PROGRESS);

        service.updateTask(task);

        verify(repository, times(1)).updateTask(task);
    }

    @Test
    @DisplayName("Обновление задачи с null выбрасывает NullPointerException")
    void updateTask_withNull_shouldThrowException() {
        assertThrows(NullPointerException.class, () -> service.updateTask(null));
    }

    @Test
    @DisplayName("Успешное удаление задачи")
    void deleteTask_shouldCallRepositoryDeleteTask() {
        int taskId = 3;

        service.deleteTask(taskId);

        verify(repository, times(1)).deleteTask(taskId);
    }

    @Test
    @DisplayName("Удаление задачи с несуществующим ID вызывает репозиторий")
    void deleteTask_withNonExistentId_shouldCallRepository() {
        int nonExistentId = 999;
        // Предполагаем, что репозиторий ничего не делает, но вызов есть
        service.deleteTask(nonExistentId);
        verify(repository, times(1)).deleteTask(nonExistentId);
    }

    @Test
    @DisplayName("Получение всех задач возвращает список из репозитория")
    void getAllTasks_shouldReturnRepositoryList() {
        List<Task> mockList = List.of(
                new Task(1, "T1", "D1", LocalDate.now(), TaskStatus.TODO),
                new Task(2, "T2", "D2", LocalDate.now(), TaskStatus.DONE)
        );

        when(repository.getAllTasks()).thenReturn(mockList);

        List<Task> result = service.getAllTasks();

        assertEquals(mockList, result);
    }

    @Test
    @DisplayName("Получение задачи с несуществующим ID возвращает null")
    void getTaskById_withNonExistentId_shouldReturnNull() {
        int nonExistentId = 999;
        when(repository.getTaskById(nonExistentId)).thenReturn(null);

        Task result = service.getTaskById(nonExistentId);

        assertNull(result);
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
    @DisplayName("Фильтрация задач с null статусом выбрасывает NullPointerException")
    void filterTasksByStatus_withNull_shouldThrowException() {
        assertThrows(NullPointerException.class, () -> service.filterTasksByStatus(null));
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
