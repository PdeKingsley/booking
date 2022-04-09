package com.hotel.booking;

import com.hotel.booking.entity.Booking;
import com.hotel.booking.entity.BookingLibrary;
import com.hotel.booking.entity.Rooms;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookingApplication.class)
class BookingApplicationTests {
    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;
    @Test
    void contextLoads() {

    }

    @Before
    public void setup(){
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
        BookingLibrary bookingLibrary = context.getBean(BookingLibrary.class);
        Rooms rooms = context.getBean(Rooms.class);
        for (int i = 0; i < 10; i++) {
            String guest = "Guest" + i;
            List<Booking> bookings = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                int roomNumber = i * 10 + j;
                String createdAt = "2020-" + i + "-" + j;
                Booking booking = new Booking((long) roomNumber,roomNumber, guest, createdAt);
                bookings.add(booking);
                bookingLibrary.getBookings().add(booking);
                bookingLibrary.getBookingSummary().add(booking.getGuest() + " " + booking.getCreatedAt() + " " + booking.getRoom());
                bookingLibrary.getGuestInfo().put(guest, bookings);
                List<Integer> roomsMap = new ArrayList<>();
                roomsMap.add(roomNumber);
                rooms.getRoomsMap().put(createdAt, roomsMap);
            }
        }
    }

/*    @Test
    public void addBookingIfNumberInvalid(){
        mvc.perform(post("/booking/add?room=231&guest=Guest1&createdAt=2020-01-01"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }*/
}
