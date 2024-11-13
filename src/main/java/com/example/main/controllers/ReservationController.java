package com.example.main.controllers;

import com.example.main.model.Reservation;
import com.example.main.model.dto.ReservationRequestDTO;
import com.example.main.repository.ReservationRepository;
import com.example.main.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {
    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ReservationService reservationService;
    
    @GetMapping
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }
    
    @PostMapping
    public Reservation createReservation(@RequestBody ReservationRequestDTO reservationRequestDTO) {
        return reservationService.makeReservation(reservationRequestDTO);
    }
    
    @GetMapping("/room/{roomId}")
    public List<Reservation> getReservationsByRoom(@PathVariable Long roomId) {
        return reservationRepository.findByRoomId(roomId);
    }
}