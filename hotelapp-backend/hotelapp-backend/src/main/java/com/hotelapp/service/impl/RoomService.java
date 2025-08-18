package com.hotelapp.service.impl;

import com.hotelapp.dto.RoomDTO;
import com.hotelapp.enums.RoomType;
import com.hotelapp.mapper.RoomMapper;
import com.hotelapp.model.Room;
import com.hotelapp.repo.RoomRepo;
import com.hotelapp.service.IRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoomService implements IRoomService {

    @Autowired
    private RoomRepo roomRepo;

    @Autowired
    private RoomMapper roomMapper;

    @Override
    public List<RoomDTO> getAllRooms() {
        return roomRepo.findAll()
                .stream()
                .map(roomMapper)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<RoomDTO> getRoomById(Long id) {
        return roomRepo.findById(id)
                .map(roomMapper);
    }

    @Override
    public List<RoomDTO> getAvailableRooms() {
        return roomRepo.findByAvailableTrue()
                .stream()
                .map(roomMapper)
                .collect(Collectors.toList());
    }

    @Override
    public List<RoomDTO> getRoomsByType(RoomType roomType) {
        return roomRepo.findByRoomType(roomType)
                .stream()
                .map(roomMapper)
                .collect(Collectors.toList());
    }

    @Override
    public List<RoomDTO> getAvailableRoomsForDates(LocalDate checkInDate, LocalDate checkOutDate) {
        if(checkInDate.isAfter(checkOutDate)){
            throw new RuntimeException("Check-in data must be before check-out data");
        }
        return roomRepo.findAvailableRoomsForDates(checkInDate, checkOutDate)
                .stream()
                .map(roomMapper)
                .collect(Collectors.toList());
    }

    @Override
    public RoomDTO createRoom(RoomDTO roomDTO) {
        Room room = roomMapper.toEntity(roomDTO);
        room.setAvailable(true);
        Room savedRoom = roomRepo.save(room);
        return roomMapper.apply(savedRoom);
    }

    @Override
    public RoomDTO updateRoom(Long id, RoomDTO roomDTO) {
        Optional<Room> existingRoom = roomRepo.findById(id);
        if(existingRoom.isEmpty()){
            throw new RuntimeException("Room not found with id: " + id);
        }
        Room room = existingRoom.get();
        room.setRoomNumber(roomDTO.roomNumber());
        room.setRoomType(roomDTO.roomType());
        room.setPricePerNight(roomDTO.pricePerNight());
        room.setCapacity(roomDTO.capacity());
        room.setDescription(roomDTO.description());
        if(room.getAvailable() != null){
            room.setAvailable(roomDTO.available());
        }
        Room savedRoom = roomRepo.save(room);
        return roomMapper.apply(savedRoom);
    }

    @Override
    public void deleteRoom(Long id) {
        if(!roomRepo.existsById(id)){
            throw new RuntimeException("Room not found with id: " + id);
        }
        roomRepo.deleteById(id);
    }
}
