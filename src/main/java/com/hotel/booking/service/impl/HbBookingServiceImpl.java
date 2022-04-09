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
        //若房间已被预定，或房间号不合法，则返回0
        if(booking.getRoom() < rooms.getNums() && !bookingLibrary.getBookingSummary().contains(booking.getDate() + " " + booking.getRoom())){
            try {
                //获取写锁
                lock.writeLock().lock();
                //插入预定信息
                bookingLibrary.getBookings().add(booking);
                //插入客户预定信息
                List<Booking> bookings = bookingLibrary.getGuestInfo().containsKey(booking.getGuest()) ?
                        bookingLibrary.getGuestInfo().get(booking.getGuest()) : new ArrayList<>();
                bookings.add(booking);
                bookingLibrary.getGuestInfo().put(booking.getGuest(), bookings);
                //插入房间预定摘要
                bookingLibrary.getBookingSummary().add(booking.getDate() + " " + booking.getRoom());
                //插入房间每日预定信息
                List<Integer> roomsMap = rooms.getRoomsMap().containsKey(booking.getDate()) ?
                        rooms.getRoomsMap().get(booking.getDate()) : new ArrayList<>();
                roomsMap.add(booking.getRoom());
                rooms.getRoomsMap().put(booking.getDate(),roomsMap);
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
                for(int room : rooms.getRoomsMap().get(date)){
                    res.remove(rooms.getRooms().get(room));
                }
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
            res.addAll(bookingLibrary.getGuestInfo().get(guest));
        }
        return res;
    }
}
