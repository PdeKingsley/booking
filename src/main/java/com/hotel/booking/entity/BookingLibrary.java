package com.hotel.booking.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

@Data
public class BookingLibrary {
    List<Booking> bookings;
    HashSet<String> bookingSummary;
    Map<String,List<Booking>> guestInfo;

    public BookingLibrary(){
        this.bookingSummary = new HashSet<>();
        this.bookings = new ArrayList<>(16);
        this.guestInfo = new HashMap<>(100);
    }
}
