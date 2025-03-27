package co.pruebatecnica.service;

import co.pruebatecnica.exception.DuplicateTaskException;
import co.pruebatecnica.exception.InvalidTaskStateException;
import co.pruebatecnica.exception.TaskNotFoundException;
import co.pruebatecnica.model.Priority;
import co.pruebatecnica.model.SortingStrategy;
import co.pruebatecnica.model.Task;

import java.util.List;

public interface TaskManager {
    void addTask(Task task) throws DuplicateTaskException;
    void markTaskAsCompleted(String taskId)
            throws TaskNotFoundException, InvalidTaskStateException;
    void removeCompletedTasks();
    List<Task> getAllTasks();
    List<Task> getPendingTasks();
    List<Task> getCompletedTasks();
    List<Task> getTasksByPriority(Priority priority);
    void setSortingStrategy(SortingStrategy strategy);
    Task getTask(String taskId) throws TaskNotFoundException;
}