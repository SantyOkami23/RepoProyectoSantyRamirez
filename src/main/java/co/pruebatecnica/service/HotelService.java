package co.pruebatecnica.service;


import co.pruebatecnica.exception.InvalidRoomOperationException;
import co.pruebatecnica.exception.RoomNotAvailableException;
import co.pruebatecnica.exception.RoomNotFoundException;
import co.pruebatecnica.model.Room;
import co.pruebatecnica.model.RoomStatus;
import co.pruebatecnica.model.RoomType;
import co.pruebatecnica.strategy.PricingStrategy;

import java.util.List;

public interface HotelService {
    void addRoom(Room room) throws InvalidRoomOperationException;
    void addRooms(List<Room> rooms) throws InvalidRoomOperationException;
    Room reserveRoom(RoomType type) throws RoomNotAvailableException;
    void releaseRoom(int roomNumber) throws RoomNotFoundException, InvalidRoomOperationException;
    List<Room> getAvailableRooms();
    List<Room> getAvailableRoomsByType(RoomType type);
    Room getRoomInfo(int roomNumber) throws RoomNotFoundException;
    void setRoomStatus(int roomNumber, RoomStatus status)
            throws RoomNotFoundException, InvalidRoomOperationException;
    void setPricingStrategy(PricingStrategy strategy);
    List<Room> getAllRooms();
}