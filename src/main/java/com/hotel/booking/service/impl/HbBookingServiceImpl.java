package com.hotel.booking.service.impl;

import com.hotel.booking.entity.Booking;
import com.hotel.booking.entity.BookingLibrary;
import com.hotel.booking.entity.Room;
import com.hotel.booking.entity.Rooms;
import com.hotel.booking.service.HbBookingService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class HbBookingServiceImpl implements HbBookingService {
    @Autowired
    private BookingLibrary bookingLibrary;
    @Autowired
    private Rooms rooms;
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    @Override
    public int add(Booking booking) {
        if(booking.getRoom() < rooms.getNums()){
            try {
                lock.writeLock().lock();
                bookingLibrary.getBookings().add(booking);
                bookingLibrary.getGuestInfo().put(booking.getGuest(), booking);
                rooms.getRoomsMap().put(booking.getCreatedAt(),booking.getRoom());
            }finally {
                lock.writeLock().unlock();
            }
            return 1;
        }else {
            return 0;
        }
    }

    @Override
    public List<Room> getRoomsByDate(String date) {
        List<Room> res = new ArrayList<>(rooms.getRooms());
        try {
            lock.readLock().lock();
            if(rooms.getRoomsMap().containsKey(date)){
                res.remove(rooms.getRoomsMap().get(date));
            }
        }finally {
            lock.readLock().unlock();
        }
        return res;
    }

    @Override
    public List<Booking> getBookingByGuest(String guest) {
        List<Booking> res = new ArrayList<>();
        if(bookingLibrary.getGuestInfo().containsKey(guest)){
            res.add(bookingLibrary.getGuestInfo().get(guest));
        }
        return res;
    }
}
