package co.pruebatecnica.model;

import java.util.List;
import java.util.stream.Collectors;

public class PrioritySortingStrategy implements SortingStrategy {
    @Override
    public List<Task> sort(List<Task> tasks) {
        return tasks.stream()
                .sorted((t1, t2) -> Integer.compare(
                        t1.getPriority().getWeight(),
                        t2.getPriority().getWeight()))
                .collect(Collectors.toList());
    }
}