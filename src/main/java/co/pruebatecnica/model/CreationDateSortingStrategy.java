package co.pruebatecnica.model;

import java.util.List;
import java.util.stream.Collectors;

public class CreationDateSortingStrategy implements SortingStrategy {
    @Override
    public List<Task> sort(List<Task> tasks) {
        return tasks.stream()
                .sorted((t1, t2) -> t1.getCreationDate().compareTo(t2.getCreationDate()))
                .collect(Collectors.toList());
    }
}