package model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Task {
    private static int id;
    private static String title;
    private static String description;
    private static LocalDate dueDate;
    private static TaskStatus status;
}
