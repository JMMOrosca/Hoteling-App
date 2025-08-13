package com.hotelapp.service.impl;

import com.hotelapp.enums.BookingStatus;
import com.hotelapp.model.Booking;
import com.hotelapp.model.Customer;
import com.hotelapp.model.Room;
import com.hotelapp.repo.BookingRepo;
import com.hotelapp.repo.CustomerRepo;
import com.hotelapp.repo.RoomRepo;
import com.hotelapp.service.IBookingService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.RuntimeMBeanException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService implements IBookingService {

    @Autowired
    private BookingRepo bookingRepo;

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private RoomRepo roomRepo;

    Customer customer = new Customer();
    Room room = new Room();
    @Override
    public List<Booking> getAllBookings() {
        return bookingRepo.findAll();
    }

    @Override
    public Optional<Booking> getBookingById(Long id) {
        return bookingRepo.findById(id);
    }

    @Override
    public List<Booking> getBookingsByCustomerId(Long customerId) {
        return bookingRepo.findByCustomerId(customerId);
    }

    @Override
    public List<Booking> getBookingsByStatus(BookingStatus status) {
        return bookingRepo.findByStatus(status);
    }

    @Override
    @Transactional
    public Booking createBooking(Booking booking) {
        if(booking.getCheckInDate().isAfter(booking.getCheckOutDate())){
            throw new RuntimeException("Check-in date must be before check-out date");
        }
        if(booking.getCheckInDate().isBefore(LocalDate.now())){
            throw new RuntimeException("Check-in date cannot be in the past");
        }
        Customer newCustomer = customerRepo.findById(customer.getId())
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + customer.getId()));
        Room newRoom = roomRepo.findById(room.getId())
                .orElseThrow(() -> new RuntimeException("Room not found with id: " + room.getId()));

        if(!room.getAvailable()){
            throw new RuntimeException("Room is not available");
        }
        List<Room> availableRooms = roomRepo.findAvailableRoomsForDates(
                booking.getCheckInDate(), booking.getCheckOutDate());

        boolean roomAvailbleForDates = availableRooms.stream()
                .anyMatch(r -> r.getId().equals(room.getId()));
        if(!roomAvailbleForDates){
            throw new RuntimeException("Room is not avilable for the specified dates");
        }
        if(booking.getNumberOfGuests() > room.getCapacity()){
            throw new RuntimeException("Number of guests exceeds room capacity");
        }

        long numberOfNights = ChronoUnit.DAYS.between(booking.getCheckInDate(), booking.getCheckOutDate());
        double totalAmount = room.getPricePerNight() * numberOfNights;

        Booking newBooking = new Booking();
        newBooking.setCustomer(newCustomer);
        newBooking.setRoom(newRoom);
        newBooking.setCheckInDate(booking.getCheckInDate());
        booking.setCheckOutDate(booking.getCheckOutDate());
        booking.setNumberOfGuests(booking.getNumberOfGuests());
        booking.setTotalAmount(totalAmount);
        booking.setStatus(BookingStatus.CONFIRMED);

        return bookingRepo.save(newBooking);
    }

    @Override
    @Transactional
    public Booking updateBookingStatus(Long id, BookingStatus status) {
        Booking booking = bookingRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + id));
        BookingStatus oldStatus = booking.getStatus();
        booking.setStatus(status);

        return bookingRepo.save(booking);
    }

    @Override
    @Transactional
    public void deleteBooking(Long id) {
        Booking booking = bookingRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + id));
        bookingRepo.deleteById(id);
    }
}
