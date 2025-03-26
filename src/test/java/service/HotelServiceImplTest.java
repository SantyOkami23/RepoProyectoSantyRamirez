package service;

import co.pruebatecnica.exception.InvalidRoomOperationException;
import co.pruebatecnica.exception.RoomNotAvailableException;
import co.pruebatecnica.exception.RoomNotFoundException;
import co.pruebatecnica.model.Room;
import co.pruebatecnica.model.RoomStatus;
import co.pruebatecnica.model.RoomType;
import co.pruebatecnica.service.HotelService;
import co.pruebatecnica.service.HotelServiceImpl;
import co.pruebatecnica.strategy.SeasonalPricingStrategy;
import org.junit.jupiter.api.*;


import static org.junit.jupiter.api.Assertions.*;

class HotelServiceImplTest {
    private HotelService hotelService;

    @BeforeEach
    void setUp() throws InvalidRoomOperationException {
        hotelService = new HotelServiceImpl();
        hotelService.addRoom(new Room(101, RoomType.INDIVIDUAL));
        hotelService.addRoom(new Room(102, RoomType.INDIVIDUAL));
        hotelService.addRoom(new Room(201, RoomType.DOUBLE));
    }

    @Test
    void testAddRoom() {
        assertEquals(3, hotelService.getAllRooms().size());
    }

    @Test
    void testAddDuplicateRoom() {
        assertThrows(InvalidRoomOperationException.class, () -> {
            hotelService.addRoom(new Room(101, RoomType.INDIVIDUAL));
        });
    }

    @Test
    void testReserveAvailableRoom() throws RoomNotAvailableException {
        Room reservedRoom = hotelService.reserveRoom(RoomType.DOUBLE);
        assertEquals(RoomStatus.OCCUPIED, reservedRoom.getStatus());
        assertEquals(201, reservedRoom.getNumber());
    }

    @Test
    void testReserveUnavailableRoom() throws RoomNotAvailableException {
        // Reservar la única habitación doble
        hotelService.reserveRoom(RoomType.DOUBLE);

        assertThrows(RoomNotAvailableException.class, () -> {
            hotelService.reserveRoom(RoomType.DOUBLE);
        });
    }

    @Test
    void testReleaseRoom() throws RoomNotAvailableException, RoomNotFoundException, InvalidRoomOperationException {
        Room reservedRoom = hotelService.reserveRoom(RoomType.DOUBLE);
        hotelService.releaseRoom(reservedRoom.getNumber());
        assertEquals(RoomStatus.AVAILABLE, reservedRoom.getStatus());
    }

    @Test
    void testReleaseNonExistentRoom() {
        assertThrows(RoomNotFoundException.class, () -> {
            hotelService.releaseRoom(999);
        });
    }

    @Test
    void testReleaseNotOccupiedRoom() {
        assertThrows(InvalidRoomOperationException.class, () -> {
            hotelService.releaseRoom(101);
        });
    }

    @Test
    void testSetRoomStatusToMaintenance() throws RoomNotFoundException, InvalidRoomOperationException {
        hotelService.setRoomStatus(101, RoomStatus.MAINTENANCE);
        assertEquals(RoomStatus.MAINTENANCE, hotelService.getRoomInfo(101).getStatus());
    }

    @Test
    void testSetOccupiedRoomToMaintenance() throws RoomNotAvailableException {
        hotelService.reserveRoom(RoomType.DOUBLE);
        assertThrows(InvalidRoomOperationException.class, () -> {
            hotelService.setRoomStatus(201, RoomStatus.MAINTENANCE);
        });
    }

    @Test
    void testPricingStrategy() throws RoomNotFoundException {
        // Precio base individual: 100
        assertEquals(100.0, hotelService.getRoomInfo(101).getCurrentPrice());

        // Cambiar a estrategia estacional (20% más)
        hotelService.setPricingStrategy(new SeasonalPricingStrategy(1.2));
        assertEquals(120.0, hotelService.getRoomInfo(101).getCurrentPrice());
    }

    @Test
    void testGetAvailableRoomsByType() throws RoomNotAvailableException {
        assertEquals(2, hotelService.getAvailableRoomsByType(RoomType.INDIVIDUAL).size());

        // Reservar una individual
        hotelService.reserveRoom(RoomType.INDIVIDUAL);
        assertEquals(1, hotelService.getAvailableRoomsByType(RoomType.INDIVIDUAL).size());
    }
}