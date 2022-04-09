package com.hotel.booking.entity;

import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class Rooms {
    private Integer nums;
    private List<Room> rooms;
    private Map<String,Integer> roomsMap;

    public Rooms(int nums, List<Room> rooms, HashMap<String,Integer> roomsMap) {
        this.nums = nums;
        this.rooms = rooms;
        this.roomsMap = roomsMap;
    }
}
