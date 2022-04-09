package com.hotel.booking.service;

import com.hotel.booking.entity.Booking;
import com.hotel.booking.entity.Room;

import java.util.List;

public interface HbBookingService {
    /**
     * 预定房间
     * @param booking
     * @return
     */
    int add(Booking booking);

    /**
     * 根据日期获取可预订房间信息
     * @param date
     * @return
     */
    List<Room> getRoomsByDate(String date);

    /**
     * 根据客人名称获取其预订信息
     * @param guest
     * @return
     */
    List<Booking> getBookingByGuest(String guest);
}
