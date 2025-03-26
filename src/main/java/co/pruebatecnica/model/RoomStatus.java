package co.pruebatecnica.model;
public enum RoomStatus {
    AVAILABLE("Disponible"),
    OCCUPIED("Ocupada"),
    MAINTENANCE("En mantenimiento");

    private final String description;

    RoomStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}