package co.pruebatecnica.runtareas;


import co.pruebatecnica.exception.DuplicateTaskException;
import co.pruebatecnica.exception.InvalidTaskStateException;
import co.pruebatecnica.exception.TaskNotFoundException;
import co.pruebatecnica.model.CreationDateSortingStrategy;
import co.pruebatecnica.model.Priority;
import co.pruebatecnica.model.Task;
import co.pruebatecnica.service.TaskManager;
import co.pruebatecnica.service.TaskManagerImpl;


public class Main {
    public static void main(String[] args) {
        try {

            TaskManager taskManager = new TaskManagerImpl();

            // Agregar tareas
            taskManager.addTask(new Task("T1", "Terminar informe mensual", Priority.HIGH));
            taskManager.addTask(new Task("T2", "Revisar correos electrónicos", Priority.MEDIUM));
            taskManager.addTask(new Task("T3", "Organizar reunión de equipo", Priority.HIGH));
            taskManager.addTask(new Task("T4", "Actualizar documentación", Priority.LOW));

            // Mostrar todas las tareas ordenadas por prioridad
            System.out.println("=== Todas las tareas (orden por prioridad) ===");
            taskManager.getAllTasks().forEach(System.out::println);


            System.out.println("\n=== Completando tarea T1 ===");
            taskManager.markTaskAsCompleted("T1");
            System.out.println("Tarea T1 completada");


            System.out.println("\n=== Tareas pendientes ===");
            taskManager.getPendingTasks().forEach(System.out::println);


            System.out.println("\n=== Cambiando a orden por fecha de creación ===");
            taskManager.setSortingStrategy(new CreationDateSortingStrategy());
            taskManager.getAllTasks().forEach(System.out::println);


            System.out.println("\n=== Eliminando tareas completadas ===");
            taskManager.removeCompletedTasks();
            System.out.println("Tareas restantes:");
            taskManager.getAllTasks().forEach(System.out::println);


            try {
                System.out.println("\n=== Intentando completar tarea ya completada ===");
                taskManager.markTaskAsCompleted("T1");
            } catch (TaskNotFoundException | InvalidTaskStateException e) {
                System.out.println("Error: " + e.getMessage());
            }

        } catch (DuplicateTaskException | TaskNotFoundException | InvalidTaskStateException e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
