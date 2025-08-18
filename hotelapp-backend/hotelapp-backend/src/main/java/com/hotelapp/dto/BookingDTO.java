package com.hotelapp.dto;

import com.hotelapp.enums.BookingStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record BookingDTO(
        Long id,
        Long customerId,
        Long roomId,
        LocalDate checkInDate,
        LocalDate checkOutDate,
        Integer numberOfGuests,
        Double totalAmount,
        BookingStatus status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
