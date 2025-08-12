package com.hotelapp.repo;

import com.hotelapp.enums.BookingStatus;
import com.hotelapp.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepo extends JpaRepository<Booking, Long> {
    List<Booking> findByCustomerId(Long customerId);
    List<Booking> findByStatus(BookingStatus status);
    List<Booking> findByRoomId(Long roomId);
}
