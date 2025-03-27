package co.pruebatecnica;

import co.pruebatecnica.exception.RoomNotAvailableException;
import co.pruebatecnica.model.Room;
import co.pruebatecnica.model.RoomStatus;
import co.pruebatecnica.model.RoomType;
import co.pruebatecnica.service.HotelService;
import co.pruebatecnica.service.HotelServiceImpl;
import co.pruebatecnica.strategy.SeasonalPricingStrategy;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {

            HotelService hotelService = new HotelServiceImpl();


            hotelService.setPricingStrategy(new SeasonalPricingStrategy(1.2));


            List<Room> rooms = Arrays.asList(
                    new Room(101, RoomType.INDIVIDUAL),
                    new Room(102, RoomType.INDIVIDUAL),
                    new Room(201, RoomType.DOUBLE),
                    new Room(202, RoomType.DOUBLE),
                    new Room(301, RoomType.SUITE),
                    new Room(302, RoomType.SUITE)
            );

            hotelService.addRooms(rooms);


            System.out.println("=== Todas las habitaciones ===");
            hotelService.getAllRooms().forEach(System.out::println);


            System.out.println("\n=== Reservando habitación doble ===");
            Room reservedRoom = hotelService.reserveRoom(RoomType.DOUBLE);
            System.out.println("Habitación reservada: " + reservedRoom);


            System.out.println("\n=== Habitaciones disponibles ===");
            hotelService.getAvailableRooms().forEach(System.out::println);


            System.out.println("\n=== Reservando otra habitación doble ===");
            Room anotherReservedRoom = hotelService.reserveRoom(RoomType.DOUBLE);
            System.out.println("Habitación reservada: " + anotherReservedRoom);


            try {
                System.out.println("\n=== Intentando reservar tercera habitación doble ===");
                hotelService.reserveRoom(RoomType.DOUBLE);
            } catch (RoomNotAvailableException e) {
                System.out.println("Error: " + e.getMessage());
            }


            System.out.println("\n=== Liberando habitación 201 ===");
            hotelService.releaseRoom(201);
            System.out.println("Habitación 201 liberada");


            System.out.println("\n=== Estado actual ===");
            hotelService.getAllRooms().forEach(System.out::println);


            System.out.println("\n=== Poniendo habitación 101 en mantenimiento ===");
            hotelService.setRoomStatus(101, RoomStatus.MAINTENANCE);
            System.out.println("Habitación 101 ahora en mantenimiento");


            try {
                System.out.println("\n=== Intentando reservar habitación en mantenimiento ===");
                hotelService.reserveRoom(RoomType.INDIVIDUAL);
            } catch (RoomNotAvailableException e) {
                System.out.println("Error: " + e.getMessage());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}