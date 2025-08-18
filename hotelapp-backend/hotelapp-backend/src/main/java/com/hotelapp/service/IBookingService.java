package com.hotelapp.service;

import com.hotelapp.dto.BookingDTO;
import com.hotelapp.enums.BookingStatus;
import com.hotelapp.model.Booking;

import java.util.List;
import java.util.Optional;

public interface IBookingService {
    public List<BookingDTO> getAllBookings();
    public Optional<BookingDTO> getBookingById(Long id);
    public List<BookingDTO> getBookingsByCustomerId(Long customerId);
    public List<BookingDTO> getBookingsByStatus(BookingStatus status);
    public BookingDTO createBooking(BookingDTO bookingDTO);
    public BookingDTO updateBookingStatus(Long id, BookingStatus status);
    public void deleteBooking(Long id);
}
