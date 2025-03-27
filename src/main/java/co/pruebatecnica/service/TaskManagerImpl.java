package co.pruebatecnica.service;


import co.pruebatecnica.exception.DuplicateTaskException;
import co.pruebatecnica.exception.InvalidTaskStateException;
import co.pruebatecnica.exception.TaskNotFoundException;
import co.pruebatecnica.model.*;

import java.util.*;
import java.util.stream.Collectors;

public class TaskManagerImpl implements TaskManager {
    private final Map<String, Task> tasks;
    private SortingStrategy sortingStrategy;

    public TaskManagerImpl() {
        this.tasks = new HashMap<>();
        this.sortingStrategy = new PrioritySortingStrategy();
    }

    @Override
    public void addTask(Task task) throws DuplicateTaskException {
        if (task == null) {
            throw new IllegalArgumentException("La tarea no puede ser nula");
        }
        if (tasks.containsKey(task.getId())) {
            throw new DuplicateTaskException("Ya existe una tarea con ID: " + task.getId());
        }
        tasks.put(task.getId(), task);
    }

    @Override
    public void markTaskAsCompleted(String taskId)
            throws TaskNotFoundException, InvalidTaskStateException {
        Task task = tasks.get(taskId);
        if (task == null) {
            throw new TaskNotFoundException("Tarea no encontrada con ID: " + taskId);
        }
        if (task.getStatus() == TaskStatus.COMPLETED) {
            throw new InvalidTaskStateException("La tarea ya está completada");
        }
        task.markAsCompleted();
    }

    @Override
    public void removeCompletedTasks() {
        tasks.values().removeIf(task -> task.getStatus() == TaskStatus.COMPLETED);
    }

    @Override
    public List<Task> getAllTasks() {
        return sortingStrategy.sort(new ArrayList<>(tasks.values()));
    }

    @Override
    public List<Task> getPendingTasks() {
        return tasks.values().stream()
                .filter(task -> task.getStatus() == TaskStatus.PENDING)
                .sorted((t1, t2) -> Integer.compare(
                        t1.getPriority().getWeight(),
                        t2.getPriority().getWeight()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Task> getCompletedTasks() {
        return tasks.values().stream()
                .filter(task -> task.getStatus() == TaskStatus.COMPLETED)
                .sorted((t1, t2) -> t2.getCompletionDate().compareTo(t1.getCompletionDate()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Task> getTasksByPriority(Priority priority) {
        return tasks.values().stream()
                .filter(task -> task.getPriority() == priority)
                .sorted((t1, t2) -> t1.getCreationDate().compareTo(t2.getCreationDate()))
                .collect(Collectors.toList());
    }

    @Override
    public void setSortingStrategy(SortingStrategy strategy) {
        this.sortingStrategy = strategy;
    }

    @Override
    public Task getTask(String taskId) throws TaskNotFoundException {
        Task task = tasks.get(taskId);
        if (task == null) {
            throw new TaskNotFoundException("Tarea no encontrada con ID: " + taskId);
        }
        return task;
    }
}