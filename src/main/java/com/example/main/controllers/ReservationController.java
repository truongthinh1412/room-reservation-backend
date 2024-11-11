package com.example.main.controllers;

import com.example.main.model.Reservation;
import com.example.main.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {
    @Autowired
    private ReservationRepository reservationRepository;
    
    @GetMapping
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }
    
    @PostMapping
    public Reservation createReservation(@RequestBody Reservation reservation) {
        return reservationRepository.save(reservation);
    }
    
    @GetMapping("/room/{roomId}")
    public List<Reservation> getReservationsByRoom(@PathVariable Long roomId) {
        return reservationRepository.findByRoomId(roomId);
    }
}