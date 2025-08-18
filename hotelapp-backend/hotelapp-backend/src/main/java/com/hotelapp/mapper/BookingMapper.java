package com.hotelapp.mapper;

import com.hotelapp.dto.BookingDTO;
import com.hotelapp.model.Booking;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class BookingMapper implements Function<Booking, BookingDTO> {


    @Override
    public BookingDTO apply(Booking booking) {
        return new BookingDTO(
                booking.getId(),
                booking.getCustomer().getId(),
                booking.getRoom().getId(),
                booking.getCheckInDate(),
                booking.getCheckOutDate(),
                booking.getNumberOfGuests(),
                booking.getTotalAmount(),
                booking.getStatus(),
                booking.getCreatedAt(),
                booking.getUpdatedAt()
        );
    }

    public Booking toEntity(BookingDTO dto){
        return Booking.builder()
                .id(dto.id())
                .checkInDate(dto.checkInDate())
                .checkOutDate(dto.checkOutDate())
                .numberOfGuests(dto.numberOfGuests())
                .totalAmount(dto.totalAmount())
                .status(dto.status())
                .createdAt(dto.createdAt())
                .updatedAt(dto.updatedAt())
                .build();
    }
}
