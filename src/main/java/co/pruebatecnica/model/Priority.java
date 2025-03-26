package co.pruebatecnica.model;
public enum Priority {
    HIGH("Alta", 1),
    MEDIUM("Media", 2),
    LOW("Baja", 3);

    private final String description;
    private final int weight;

    Priority(String description, int weight) {
        this.description = description;
        this.weight = weight;
    }

    public String getDescription() {
        return description;
    }

    public int getWeight() {
        return weight;
    }
}