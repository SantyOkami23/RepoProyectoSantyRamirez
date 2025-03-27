package service;


import co.pruebatecnica.exception.DuplicateTaskException;
import co.pruebatecnica.exception.InvalidTaskStateException;
import co.pruebatecnica.exception.TaskNotFoundException;
import co.pruebatecnica.model.CreationDateSortingStrategy;
import co.pruebatecnica.model.Priority;
import co.pruebatecnica.model.Task;
import co.pruebatecnica.model.TaskStatus;
import co.pruebatecnica.service.TaskManager;
import co.pruebatecnica.service.TaskManagerImpl;
import org.junit.jupiter.api.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class TaskManagerImplTest {
    private TaskManager taskManager;
    private Task highPriorityTask;
    private Task mediumPriorityTask;
    private Task lowPriorityTask;

    @BeforeEach
    void setUp() throws DuplicateTaskException {
        taskManager = new TaskManagerImpl();
        highPriorityTask = new Task("T1", "Tarea importante", Priority.HIGH);
        mediumPriorityTask = new Task("T2", "Tarea media", Priority.MEDIUM);
        lowPriorityTask = new Task("T3", "Tarea baja", Priority.LOW);

        taskManager.addTask(highPriorityTask);
        taskManager.addTask(mediumPriorityTask);
        taskManager.addTask(lowPriorityTask);
    }

    @Test
    void testAddTask() {
        assertEquals(3, taskManager.getAllTasks().size());
    }

    @Test
    void testAddDuplicateTask() {
        assertThrows(DuplicateTaskException.class, () -> {
            taskManager.addTask(new Task("T1", "Tarea duplicada", Priority.HIGH));
        });
    }

    @Test
    void testMarkTaskAsCompleted() throws TaskNotFoundException, InvalidTaskStateException {
        taskManager.markTaskAsCompleted("T1");
        assertEquals(TaskStatus.COMPLETED, taskManager.getTask("T1").getStatus());
    }

    @Test
    void testMarkNonExistentTask() {
        assertThrows(TaskNotFoundException.class, () -> {
            taskManager.markTaskAsCompleted("T99");
        });
    }

    @Test
    void testMarkAlreadyCompletedTask() throws TaskNotFoundException, InvalidTaskStateException {
        taskManager.markTaskAsCompleted("T1");
        assertThrows(InvalidTaskStateException.class, () -> {
            taskManager.markTaskAsCompleted("T1");
        });
    }

    @Test
    void testRemoveCompletedTasks() throws TaskNotFoundException, InvalidTaskStateException {
        taskManager.markTaskAsCompleted("T1");
        taskManager.markTaskAsCompleted("T2");
        taskManager.removeCompletedTasks();
        assertEquals(1, taskManager.getAllTasks().size());
        assertEquals("T3", taskManager.getAllTasks().get(0).getId());
    }

    @Test
    void testGetTasksByPriority() {
        List<Task> highPriorityTasks = taskManager.getTasksByPriority(Priority.HIGH);
        assertEquals(1, highPriorityTasks.size());
        assertEquals("T1", highPriorityTasks.get(0).getId());
    }

    @Test
    void testSortingStrategy() {
        // Por defecto ordena por prioridad (HIGH primero)
        assertEquals("T1", taskManager.getAllTasks().get(0).getId());

        // Cambiar a orden por fecha de creación
        taskManager.setSortingStrategy(new CreationDateSortingStrategy());
        assertEquals("T1", taskManager.getAllTasks().get(0).getId());
    }

    @Test
    void testGetPendingTasks() throws TaskNotFoundException, InvalidTaskStateException {
        assertEquals(3, taskManager.getPendingTasks().size());
        taskManager.markTaskAsCompleted("T1");
        assertEquals(2, taskManager.getPendingTasks().size());
    }

    @Test
    void testGetCompletedTasks() throws TaskNotFoundException, InvalidTaskStateException {
        assertEquals(0, taskManager.getCompletedTasks().size());
        taskManager.markTaskAsCompleted("T1");
        assertEquals(1, taskManager.getCompletedTasks().size());
    }
}