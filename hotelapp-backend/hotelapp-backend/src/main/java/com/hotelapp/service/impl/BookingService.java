package com.hotelapp.service.impl;

import com.hotelapp.dto.BookingDTO;
import com.hotelapp.enums.BookingStatus;
import com.hotelapp.mapper.BookingMapper;
import com.hotelapp.mapper.CustomerMapper;
import com.hotelapp.mapper.RoomMapper;
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

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookingService implements IBookingService {

    @Autowired
    private BookingRepo bookingRepo;

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private RoomRepo roomRepo;

    @Autowired
    private BookingMapper bookingMapper;

    @Autowired
    private RoomMapper roomMapper;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private EmailService emailService;

    Customer customer = new Customer();
    Room room = new Room();
    @Override
    public List<BookingDTO> getAllBookings() {
        return bookingRepo.findAll()
                .stream()
                .map(bookingMapper)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<BookingDTO> getBookingById(Long id) {
        return bookingRepo.findById(id)
                .map(bookingMapper);
    }

    @Override
    public List<BookingDTO> getBookingsByCustomerId(Long customerId) {
        return bookingRepo.findByCustomerId(customerId)
                .stream()
                .map(bookingMapper)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookingDTO> getBookingsByStatus(BookingStatus status) {
        return bookingRepo.findByStatus(status)
                .stream()
                .map(bookingMapper)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public BookingDTO createBooking(BookingDTO bookingDTO) {
        if(bookingDTO.checkInDate().isAfter(bookingDTO.checkOutDate())){
            throw new RuntimeException("Check-in date must be before check-out date");
        }
        if(bookingDTO.checkInDate().isBefore(LocalDate.now())){
            throw new RuntimeException("Check-in date cannot be in the past");
        }
        Customer customer = customerRepo.findById(bookingDTO.customerId())
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + bookingDTO.customerId()));
        Room room = roomRepo.findById(bookingDTO.roomId())
                .orElseThrow(() -> new RuntimeException("Room not found with id: " + bookingDTO.roomId()));

        if(!room.getAvailable()){
            throw new RuntimeException("Room is not available");
        }
        List<Room> availableRooms = roomRepo.findAvailableRoomsForDates(
                bookingDTO.checkInDate(), bookingDTO.checkOutDate());

        boolean roomAvailableForDates = availableRooms.stream()
                .anyMatch(r -> r.getId().equals(room.getId()));
        if(!roomAvailableForDates){
            throw new RuntimeException("Room is not available for the specified dates");
        }
        if(bookingDTO.numberOfGuests() > room.getCapacity()){
            throw new RuntimeException("Number of guests exceeds room capacity");
        }

        long numberOfNights = ChronoUnit.DAYS.between(bookingDTO.checkInDate(), bookingDTO.checkOutDate());
        double totalAmount = room.getPricePerNight() * numberOfNights;

        Booking booking = new Booking();
        booking.setCustomer(customer);
        booking.setRoom(room);
        booking.setCheckInDate(bookingDTO.checkInDate());
        booking.setCheckOutDate(bookingDTO.checkOutDate());
        booking.setNumberOfGuests(bookingDTO.numberOfGuests());
        booking.setTotalAmount(totalAmount);
        booking.setStatus(BookingStatus.CONFIRMED);

        Booking savedBooking = bookingRepo.save(booking);

        emailService.sendBookingConfirmationEmail(savedBooking);

        return bookingMapper.apply(savedBooking);
    }

    @Override
    @Transactional
    public BookingDTO updateBookingStatus(Long id, BookingStatus status) {
        Booking booking = bookingRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + id));
        BookingStatus oldStatus = booking.getStatus();
        booking.setStatus(status);
        Booking savedBooking = bookingRepo.save(booking);

        if(status == BookingStatus.CANCELLED && oldStatus != BookingStatus.CANCELLED){
            emailService.sendBookingCancellationEmail(savedBooking);
        }

        return bookingMapper.apply(savedBooking);
    }

    @Override
    @Transactional
    public void deleteBooking(Long id) {
        Booking booking = bookingRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + id));

        emailService.sendBookingCancellationEmail(booking);

        bookingRepo.deleteById(id);
    }
}
