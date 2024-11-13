package com.example.main.controllers;

import com.example.main.model.Room;
import com.example.main.model.dto.RoomFilterDTO;
import com.example.main.repository.RoomRepository;
import com.example.main.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {
    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RoomService roomService;
    
    @GetMapping
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    @PostMapping("filter")
    public Page<Room> filterRoom(@RequestBody RoomFilterDTO roomFilterDTO) {
        return roomService.getRoomsWithPaginationAndFilters(roomFilterDTO);
    }
    
    @PostMapping
    public Room createRoom(@RequestBody Room room) {
        return roomRepository.save(room);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Room> getRoomById(@PathVariable Long id) {
        return roomRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}