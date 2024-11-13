package com.example.main.service;

import com.example.main.model.Reservation;
import com.example.main.model.Room;
import com.example.main.model.dto.ReservationRequestDTO;
import com.example.main.repository.ReservationRepository;
import com.example.main.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private RoomAvailabilityService roomAvailabilityService;

    @Autowired
    private RoomRepository roomRepository;

    @Transactional
    public Reservation makeReservation(ReservationRequestDTO reservationRequestDTO) {

        Room room = roomRepository.getReferenceById(reservationRequestDTO.getRoomId());
        Reservation reservation = new Reservation(room, reservationRequestDTO.getGuestName(),
                            reservationRequestDTO.getCheckIn(), reservationRequestDTO.getCheckOut(),
                reservationRequestDTO.getStatus());
        Reservation savedReservation = reservationRepository.save(reservation);

        roomAvailabilityService.updateRoomAvailability(room, reservationRequestDTO.getCheckIn(),
                reservationRequestDTO.getCheckOut());

        return savedReservation;
    }
}
