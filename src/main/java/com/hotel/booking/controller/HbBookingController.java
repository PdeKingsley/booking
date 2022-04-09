package com.hotel.booking.controller;

import com.hotel.booking.common.CommonResult;
import com.hotel.booking.entity.Booking;
import com.hotel.booking.entity.Room;
import com.hotel.booking.service.HbBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/booking")
public class HbBookingController {
    @Autowired
    HbBookingService hbBookingService;

    @PostMapping(value = "/add")
    @ResponseBody
    public CommonResult<Booking> add(@RequestBody Booking booking) {
        int res =  hbBookingService.add(booking);
        if(res == 1){
            return new CommonResult<>(200,"success",booking);
        }else {
            return new CommonResult<>(400,"fail",null);
        }
    }

    @GetMapping(value = "/getRoomsByDate/{date}")
    @ResponseBody
    public CommonResult<List<Room>> getRoomsByDate(@PathVariable("date") String date) {
        List<Room> res = hbBookingService.getRoomsByDate(date);
        if(res.size() > 0){
            return new CommonResult<>(200,"success",res);
        }else {
            return new CommonResult<>(400,"fail",null);
        }
    }

    @GetMapping(value = "/getBookingByGuest/{guest}")
    @ResponseBody
    public CommonResult<List<Booking>> getBookingByGuest(@PathVariable("guest") String guest) {
        List<Booking> res = hbBookingService.getBookingByGuest(guest);
        if(res.size() > 0){
            return new CommonResult<>(200,"success",res);
        }else {
            return new CommonResult<>(201,"success,no info",null);
        }
    }
}
