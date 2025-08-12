package com.hotelapp.service;

import com.hotelapp.enums.BookingStatus;
import com.hotelapp.model.Booking;

import java.util.List;
import java.util.Optional;

public interface BookingService {
    public List<Booking> getAllBookings();
    public Optional<Booking> getBookingById(Long id);
    public List<Booking> getBookingsByCustomerId(Long customerId);
    public List<Booking> getBookingsByStatus(BookingStatus status);
    public Booking createBooking(Booking booking);
    public Booking updateBookingStatus(Long id, Booking status);
    public void deleteBooking(Long id);
}
