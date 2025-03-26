package co.pruebatecnica.service;

import co.pruebatecnica.exception.InvalidRoomOperationException;
import co.pruebatecnica.exception.RoomNotAvailableException;
import co.pruebatecnica.exception.RoomNotFoundException;
import co.pruebatecnica.model.Room;
import co.pruebatecnica.model.RoomStatus;
import co.pruebatecnica.model.RoomType;
import co.pruebatecnica.strategy.PricingStrategy;
import co.pruebatecnica.strategy.StandardPricingStrategy;

import java.util.*;
import java.util.stream.Collectors;

public class HotelServiceImpl implements HotelService {
    private final Map<Integer, Room> rooms;
    private PricingStrategy pricingStrategy;

    public HotelServiceImpl() {
        this.rooms = new HashMap<>();
        this.pricingStrategy = new StandardPricingStrategy();
    }

    @Override
    public void addRoom(Room room) throws InvalidRoomOperationException {
        if (room == null) {
            throw new InvalidRoomOperationException("La habitación no puede ser nula");
        }
        if (rooms.containsKey(room.getNumber())) {
            throw new InvalidRoomOperationException("Ya existe una habitación con el número " + room.getNumber());
        }
        room.setCurrentPrice(pricingStrategy.calculatePrice(room.getType().getBasePrice()));
        rooms.put(room.getNumber(), room);
    }

    @Override
    public void addRooms(List<Room> rooms) throws InvalidRoomOperationException {
        for (Room room : rooms) {
            addRoom(room);
        }
    }

    @Override
    public Room reserveRoom(RoomType type) throws RoomNotAvailableException {
        Optional<Room> availableRoom = rooms.values().stream()
                .filter(room -> room.getType() == type && room.getStatus() == RoomStatus.AVAILABLE)
                .findFirst();

        if (availableRoom.isPresent()) {
            Room room = availableRoom.get();
            room.setStatus(RoomStatus.OCCUPIED);
            return room;
        }
        throw new RoomNotAvailableException("No hay habitaciones disponibles del tipo " + type.getDescription());
    }

    @Override
    public void releaseRoom(int roomNumber) throws RoomNotFoundException, InvalidRoomOperationException {
        Room room = rooms.get(roomNumber);
        if (room == null) {
            throw new RoomNotFoundException("Habitación " + roomNumber + " no encontrada");
        }
        if (room.getStatus() != RoomStatus.OCCUPIED) {
            throw new InvalidRoomOperationException("La habitación " + roomNumber + " no está ocupada");
        }
        room.setStatus(RoomStatus.AVAILABLE);
    }

    @Override
    public List<Room> getAvailableRooms() {
        return rooms.values().stream()
                .filter(room -> room.getStatus() == RoomStatus.AVAILABLE)
                .collect(Collectors.toList());
    }

    @Override
    public List<Room> getAvailableRoomsByType(RoomType type) {
        return rooms.values().stream()
                .filter(room -> room.getType() == type && room.getStatus() == RoomStatus.AVAILABLE)
                .collect(Collectors.toList());
    }

    @Override
    public Room getRoomInfo(int roomNumber) throws RoomNotFoundException {
        Room room = rooms.get(roomNumber);
        if (room == null) {
            throw new RoomNotFoundException("Habitación " + roomNumber + " no encontrada");
        }
        return room;
    }

    @Override
    public void setRoomStatus(int roomNumber, RoomStatus status)
            throws RoomNotFoundException, InvalidRoomOperationException {
        Room room = rooms.get(roomNumber);
        if (room == null) {
            throw new RoomNotFoundException("Habitación " + roomNumber + " no encontrada");
        }

        // Validaciones adicionales
        if (room.getStatus() == RoomStatus.OCCUPIED && status == RoomStatus.MAINTENANCE) {
            throw new InvalidRoomOperationException("No se puede poner en mantenimiento una habitación ocupada");
        }

        room.setStatus(status);
    }

    @Override
    public void setPricingStrategy(PricingStrategy strategy) {
        this.pricingStrategy = strategy;

        rooms.values().forEach(room ->
                room.setCurrentPrice(pricingStrategy.calculatePrice(room.getType().getBasePrice())));
    }

    @Override
    public List<Room> getAllRooms() {
        return new ArrayList<>(rooms.values());
    }
}