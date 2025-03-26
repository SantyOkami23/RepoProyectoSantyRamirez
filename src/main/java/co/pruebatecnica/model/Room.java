package co.pruebatecnica.model;
public class Room {
    private final int number;
    private final RoomType type;
    private RoomStatus status;
    private double currentPrice;

    public Room(int number, RoomType type) {
        this.number = number;
        this.type = type;
        this.status = RoomStatus.AVAILABLE;
        this.currentPrice = type.getBasePrice();
    }

    // Getters y setters
    public int getNumber() {
        return number;
    }

    public RoomType getType() {
        return type;
    }

    public RoomStatus getStatus() {
        return status;
    }

    public void setStatus(RoomStatus status) {
        this.status = status;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    @Override
    public String toString() {
        return String.format("Habitación %d - %s - %s - Precio: $%.2f",
                number, type.getDescription(), status.getDescription(), currentPrice);
    }
}