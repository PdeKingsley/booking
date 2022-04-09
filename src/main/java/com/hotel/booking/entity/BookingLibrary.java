package com.hotel.booking.entity;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class BookingLibrary {
    List<Booking> bookings;
    Map<String,Booking> guestInfo;
}
