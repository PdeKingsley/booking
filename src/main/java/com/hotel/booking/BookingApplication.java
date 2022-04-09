package com.hotel.booking;

import com.hotel.booking.entity.Booking;
import com.hotel.booking.entity.BookingLibrary;
import com.hotel.booking.entity.Room;
import com.hotel.booking.entity.Rooms;
import com.hotel.booking.service.HbBookingService;
import com.hotel.booking.service.impl.HbBookingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SpringBootApplication
public class BookingApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookingApplication.class, args);
    }

    @Bean
    public BookingLibrary bookingLibrary() {
        BookingLibrary bookingLibrary = new BookingLibrary();
        bookingLibrary.setBookings(new ArrayList<>());
        bookingLibrary.setGuestInfo(new HashMap<>());
        bookingLibrary.getBookings().add(new Booking(1L,1, "guest1", "created1"));
        return bookingLibrary;
    }

    @Bean(value = BeanDefinition.SCOPE_PROTOTYPE)
    @Autowired(required = false)
    public Room room(int roomNumber) {
        return new Room(roomNumber,0);
    }

    @Bean
    public Rooms rooms(@Value("${room.count}") int nums) {
        List<Room> rooms = new ArrayList<>(nums);
        for (int i = 0; i < nums; i++) {
            rooms.add(room(i));
        }
        return new Rooms(nums,rooms,new HashMap<>());
    }

    @Bean
    public HbBookingService hbBookingService() {
        return new HbBookingServiceImpl();
    }
}
