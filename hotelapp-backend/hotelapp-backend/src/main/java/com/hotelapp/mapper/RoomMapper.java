package com.hotelapp.mapper;

import com.hotelapp.dto.RoomDTO;
import com.hotelapp.model.Room;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class RoomMapper implements Function<Room, RoomDTO> {
    @Override
    public RoomDTO apply(Room room) {
        return new RoomDTO(
                room.getId(),
                room.getRoomNumber(),
                room.getRoomType(),
                room.getPricePerNight(),
                room.getCapacity(),
                room.getDescription(),
                room.getAvailable()
        );
    }

    public Room toEntity(RoomDTO dto){
        return Room.builder()
                .id(dto.id())
                .roomNumber(dto.roomNumber())
                .roomType(dto.roomType())
                .pricePerNight(dto.pricePerNight())
                .capacity(dto.capacity())
                .description(dto.description())
                .available(dto.available())
                .build();
    }
}
