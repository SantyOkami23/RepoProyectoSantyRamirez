package co.pruebatecnica.model;
public enum TaskStatus {
    PENDING("Pendiente"),
    COMPLETED("Completada");

    private final String description;

    TaskStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}