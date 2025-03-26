package co.pruebatecnica.model;


public enum RoomType {
    INDIVIDUAL("Individual", 100.0),
    DOUBLE("Doble", 180.0),
    SUITE("Suite", 300.0);

    private final String description;
    private final double basePrice;

    RoomType(String description, double basePrice) {
        this.description = description;
        this.basePrice = basePrice;
    }

    public String getDescription() {
        return description;
    }

    public double getBasePrice() {
        return basePrice;
    }
}