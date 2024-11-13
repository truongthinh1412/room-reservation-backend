package com.example.main.model.dto;

import com.example.main.model.Room;

import java.util.Date;

public class ReservationRequestDTO {
    private Long roomId;
    private String guestName;
    private Date checkIn;
    private Date checkOut;
    private String status;

    public ReservationRequestDTO(Long roomId, String guestName, Date checkIn, Date checkOut, String status) {
        this.roomId = roomId;
        this.guestName = guestName;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.status = status;
    }

    public ReservationRequestDTO() {
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public Date getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(Date checkIn) {
        this.checkIn = checkIn;
    }

    public Date getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(Date checkOut) {
        this.checkOut = checkOut;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
