package com.hotelapp.repo;

import com.hotelapp.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepo extends JpaRepository<Booking, Long> {

}
