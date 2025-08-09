package com.hotelapp.model;

import com.hotelapp.enums.RoomType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Booking {
    private Long id;
    private Customer customer;
    private Room room;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Integer numberOfGuests;
    private Double totalAmount;
    private BookingStatus status;
    private LocalDateTime craetedAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();
}
