package com.example.main.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean guestFavorite;
    private String type;
    private int capacity;
    private double price;
    private String photoLink;
    @ElementCollection
    private List<String> photoLinkList;
}