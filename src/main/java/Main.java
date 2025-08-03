import controller.ConsoleInputReader;
import controller.InputReader;
import controller.TaskController;
import repository.MemoryTaskRepository;
import service.TaskServiceImpl;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        MemoryTaskRepository repository = new MemoryTaskRepository();
        InputReader reader = new ConsoleInputReader(new Scanner(System.in));
        TaskServiceImpl service = new TaskServiceImpl(repository);
        TaskController controller = new TaskController(service, reader);
        controller.run();
    }
}
