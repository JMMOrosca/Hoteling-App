package com.hotelapp.service;

import com.hotelapp.enums.RoomType;
import com.hotelapp.model.Room;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IRoomService {
    public List<Room> getAllRooms();
    public Optional<Room> getRoomById(Long id);
    public List<Room> getAvailableRooms();
    public List<Room> getRoomsByType(RoomType roomType);
    public List<Room> getAvailableRoomsForDates(LocalDate checkInDate, LocalDate checkOutDate);
    public Room createRoom(Room room);
    public Room updateRoom(Long id, Room room);
    public void deleteRoom(Long id);
}
