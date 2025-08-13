package com.hotelapp.service.impl;

import com.hotelapp.enums.RoomType;
import com.hotelapp.model.Room;
import com.hotelapp.repo.RoomRepo;
import com.hotelapp.service.IRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class RoomService implements IRoomService {

    @Autowired
    private RoomRepo roomRepo;

    @Override
    public List<Room> getAllRooms() {
        return roomRepo.findAll();
    }

    @Override
    public Optional<Room> getRoomById(Long id) {
        return roomRepo.findById(id);
    }

    @Override
    public List<Room> getAvailableRooms() {
        return roomRepo.findByAvailableTrue();
    }

    @Override
    public List<Room> getRoomsByType(RoomType roomType) {
        return roomRepo.findByRoomType(roomType);
    }

    @Override
    public List<Room> getAvailableRoomsForDates(LocalDate checkInDate, LocalDate checkOutDate) {
        if(checkInDate.isAfter(checkOutDate)){
            throw new RuntimeException("Check-in data must be before check-out data");
        }
        return roomRepo.findAvailableRoomsForDates(checkInDate, checkOutDate);
    }

    @Override
    public Room createRoom(Room room) {
        room.setAvailable(true);
        return roomRepo.save(room);
    }

    @Override
    public Room updateRoom(Long id, Room room) {
        Optional<Room> existingRoom = roomRepo.findById(id);
        if(existingRoom.isEmpty()){
            throw new RuntimeException("Room not found with id: " + id);
        }
        Room newRoom = existingRoom.get();
        newRoom.setRoomNumber(room.getRoomNumber());
        newRoom.setRoomType(room.getRoomType());
        newRoom.setPricePerNight(room.getPricePerNight());
        newRoom.setCapacity(room.getCapacity());
        newRoom.setDescription(room.getDescription());
        if(room.getAvailable() != null){
            room.setAvailable(room.getAvailable());
        }

        return roomRepo.save(newRoom);
    }

    @Override
    public void deleteRoom(Long id) {
        if(!roomRepo.existsById(id)){
            throw new RuntimeException("Room not found with id: " + id);
        }
        roomRepo.deleteById(id);
    }
}
