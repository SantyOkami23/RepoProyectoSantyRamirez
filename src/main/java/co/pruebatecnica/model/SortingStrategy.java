package co.pruebatecnica.model;

import java.util.List;

public interface SortingStrategy {
    List<Task> sort(List<Task> tasks);
}