package com.example.main.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "room_availability")
public class RoomAvailability {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;
    private Date startOfAvailability;
    private Date endOfAvailability;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Date getStartOfAvailability() {
        return startOfAvailability;
    }

    public void setStartOfAvailability(Date startOfAvailability) {
        this.startOfAvailability = startOfAvailability;
    }

    public Date getEndOfAvailability() {
        return endOfAvailability;
    }

    public void setEndOfAvailability(Date endOfAvailability) {
        this.endOfAvailability = endOfAvailability;
    }
}
