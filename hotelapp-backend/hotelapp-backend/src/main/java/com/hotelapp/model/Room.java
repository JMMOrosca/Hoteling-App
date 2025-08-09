package com.hotelapp.model;

import java.time.LocalDateTime;
import java.util.List;

public class Room {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private LocalDateTime createdAt = LocalDateTime.now();
    private List<Booking> bookings;
}
