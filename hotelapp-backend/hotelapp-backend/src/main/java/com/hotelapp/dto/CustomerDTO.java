package com.hotelapp.dto;


import com.hotelapp.model.Booking;

import java.time.LocalDateTime;

public record CustomerDTO(
        Long id,
        String firstName,
        String lastName,
        String email,
        String phone,
        LocalDateTime createdAt
){

}
