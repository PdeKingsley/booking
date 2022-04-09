package com.hotel.booking.entity;

import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

@Data
public class BookingLibrary {
    List<Booking> bookings;
    HashSet<String> bookingSummary;
    Map<String,List<Booking>> guestInfo;
}
