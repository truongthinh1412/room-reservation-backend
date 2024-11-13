package com.example.main.repository;

import com.example.main.model.Room;
import com.example.main.model.RoomAvailability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface RoomAvailabilityRepository extends JpaRepository<RoomAvailability, Long> {
    List<RoomAvailability> findByRoomAndEndOfAvailabilityAfterAndStartOfAvailabilityBefore(
            Room room, Date checkIn, Date checkOut);
}