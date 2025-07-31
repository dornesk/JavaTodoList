package model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

//геттеры, сеттеры, toString, equals/hashCode, конструктор со всеми аргументами через Lombok
@Data
@AllArgsConstructor
public class Task {
    private final int id;
    private String title;
    private String description;
    private LocalDate dueDate;
    private TaskStatus status;
}
