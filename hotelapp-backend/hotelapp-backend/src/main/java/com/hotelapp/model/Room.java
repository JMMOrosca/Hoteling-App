package com.hotelapp.model;

import com.hotelapp.enums.RoomType;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Room {
    private Long id;
    private String roomNumber;
    private RoomType roomType;
    private Double pricePerNight;
    private Integer capacity;
    private String description;
    private Boolean available = true;
    private List<Booking> bookings;}
