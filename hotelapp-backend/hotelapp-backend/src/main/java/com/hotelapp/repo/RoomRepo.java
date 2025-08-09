package com.hotelapp.repo;

import com.hotelapp.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepo extends JpaRepository<Room, Long> {
}
