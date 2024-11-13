package com.example.main.service;

import com.example.main.model.Room;
import com.example.main.model.RoomAvailability;
import com.example.main.repository.RoomAvailabilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class RoomAvailabilityService {

    @Autowired
    private RoomAvailabilityRepository roomAvailabilityRepository;

    @Transactional
    public void updateRoomAvailability(Room room, Date checkIn, Date checkOut) {
        // Find existing availability periods that overlap with the reservation dates
        List<RoomAvailability> availabilities = roomAvailabilityRepository
            .findByRoomAndEndOfAvailabilityAfterAndStartOfAvailabilityBefore(room, checkIn, checkOut);

        for (RoomAvailability availability : availabilities) {
            Date startOfAvailability = availability.getStartOfAvailability();
            Date endOfAvailability = availability.getEndOfAvailability();

            // Case 1: Reservation matches the entire availability period
            if (checkIn.equals(startOfAvailability) && checkOut.equals(endOfAvailability)) {
                roomAvailabilityRepository.delete(availability);
            }
            // Case 2: Reservation overlaps at the start of the availability period
            else if (checkIn.equals(startOfAvailability) && checkOut.before(endOfAvailability)) {
                availability.setStartOfAvailability(checkOut);
                roomAvailabilityRepository.save(availability);
            }
            // Case 3: Reservation overlaps at the end of the availability period
            else if (checkOut.equals(endOfAvailability) && checkIn.after(startOfAvailability)) {
                availability.setEndOfAvailability(checkIn);
                roomAvailabilityRepository.save(availability);
            }
            // Case 4: Reservation is in the middle of the availability period (split into two)
            else if (checkIn.after(startOfAvailability) && checkOut.before(endOfAvailability)) {
                // Split the existing availability into two periods
                RoomAvailability newAvailability = new RoomAvailability();
                newAvailability.setRoom(room);
                newAvailability.setStartOfAvailability(checkOut);
                newAvailability.setEndOfAvailability(endOfAvailability);

                // Update the end of the current availability period
                availability.setEndOfAvailability(checkIn);

                // Save both updated and new availability periods
                roomAvailabilityRepository.save(availability);
                roomAvailabilityRepository.save(newAvailability);
            }
        }
    }
}
