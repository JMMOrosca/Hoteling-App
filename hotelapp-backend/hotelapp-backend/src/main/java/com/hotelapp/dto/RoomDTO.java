package com.hotelapp.dto;

import com.hotelapp.enums.RoomType;

public record RoomDTO(
        Long id,
        String roomNumber,
        RoomType roomType,
        Double pricePerNight,
        Integer capacity,
        String description,
        Boolean available
) {
}
