package com.hotel.booking.entity;

import lombok.Data;

@Data
public class Room {
    private int roomNumber;
    private int status;

    public Room(int roomNumber){
        this.roomNumber = roomNumber;
    }
}
