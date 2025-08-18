package com.hotelapp.service;

import com.hotelapp.dto.RoomDTO;
import com.hotelapp.enums.RoomType;
import com.hotelapp.model.Room;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IRoomService {
    public List<RoomDTO> getAllRooms();
    public Optional<RoomDTO> getRoomById(Long id);
    public List<RoomDTO> getAvailableRooms();
    public List<RoomDTO> getRoomsByType(RoomType roomType);
    public List<RoomDTO> getAvailableRoomsForDates(LocalDate checkInDate, LocalDate checkOutDate);
    public RoomDTO createRoom(RoomDTO roomDTO);
    public RoomDTO updateRoom(Long id, RoomDTO roomDTO);
    public void deleteRoom(Long id);
}
