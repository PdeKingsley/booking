package com.hotel.booking.entity;

import lombok.Data;

@Data
public class Booking {
    private Long id;
    private int room;
    private String guest;
    private String createdAt;

    public Booking(Long id, int room, String guest, String created) {
        this.id = id;
        this.room = room;
        this.guest = guest;
        this.createdAt = created;
    }
}
