package com.hotelapp.repo;

import com.hotelapp.enums.RoomType;
import com.hotelapp.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RoomRepo extends JpaRepository<Room, Long> {
    List<Room> findByAvailableTrue();
    List<Room> findByRoomType(RoomType roomType);

    List<Room> findAvailableRoomsForDates(@Param("checkInDate")LocalDate checkInDate,
                                          @Param("checkOutDate")LocalDate checkOutDate);
}
