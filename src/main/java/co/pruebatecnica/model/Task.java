package co.pruebatecnica.model;

import java.time.LocalDateTime;

public class Task {
    private final String id;
    private final String description;
    private final Priority priority;
    private TaskStatus status;
    private final LocalDateTime creationDate;
    private LocalDateTime completionDate;

    public Task(String id, String description, Priority priority) {
        this.id = id;
        this.description = description;
        this.priority = priority;
        this.status = TaskStatus.PENDING;
        this.creationDate = LocalDateTime.now();
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Priority getPriority() {
        return priority;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public LocalDateTime getCompletionDate() {
        return completionDate;
    }

    // Setters
    public void markAsCompleted() {
        this.status = TaskStatus.COMPLETED;
        this.completionDate = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return String.format("[%s] %s - Prioridad: %s - Estado: %s - Creada: %s",
                id, description, priority.getDescription(), status.getDescription(),
                creationDate.toString());
    }
}