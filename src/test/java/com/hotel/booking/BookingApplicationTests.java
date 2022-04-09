package com.hotel.booking;

import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookingApplication.class)
@AutoConfigureMockMvc
class BookingApplicationTests {
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private MockMvc mvc;
    @Test
    void contextLoads() {

    }

    @Before
    public void setup(){
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    //测试房间号不存在的情况
    @Test
    public void addBookingIfNumberInvalid() throws Exception {
        MvcResult result = mvc.perform(post("/booking/add")
                .contentType("application/json")
                .content("{\"id\":\"1\",\"room\":222,\"guest\":\"John Doe\",\"date\":\"2020-01-01\"}")
                .accept("application/json")
        ).andReturn();
        Assert.assertEquals("{\"code\":400,\"message\":\"fail\",\"data\":null}", result.getResponse().getContentAsString());
    }

    //测试房间不可用的情况
    @Test
    public void addBookingIfRoomNotAvailable() throws Exception {
        mvc.perform(post("/booking/add")
                .contentType("application/json")
                .content("{\"id\":\"0\",\"room\":0,\"guest\":\"Guest0\",\"date\":\"2020-0-0\"}"));
        MvcResult result = mvc.perform(post("/booking/add")
                .contentType("application/json")
                .content("{\"id\":\"0\",\"room\":0,\"guest\":\"Guest0\",\"date\":\"2020-0-0\"}")
                .accept("application/json")
        ).andReturn();
        Assert.assertEquals("{\"code\":400,\"message\":\"fail\",\"data\":null}", result.getResponse().getContentAsString());
    }

    //测试预定成功的情况
    @Test
    public void addBookingIfSuccess() throws Exception {
        MvcResult result = mvc.perform(post("/booking/add")
                .contentType("application/json")
                .content("{\"id\":\"0\",\"room\":1,\"guest\":\"Guest0\",\"date\":\"2020-0-0\"}")
                .accept("application/json")
        ).andReturn();
        Assert.assertEquals(
                "{\"code\":200,\"message\":\"success\",\"data\":{\"id\":0,\"room\":1,\"guest\":\"Guest0\"," +
                        "\"date\":\"2020-0-0\"}}",
                result.getResponse().getContentAsString());
    }

    //无房预定的情况
    @Test
    public void getRoomsByDateIfFail() throws Exception {
        for (int i = 0; i < 200; i++) {
            mvc.perform(post("/booking/add")
                    .contentType("application/json")
                    .content("{\"id\":\"0\",\"room\":" + i + ",\"guest\":\"Guest0\",\"date\":\"2019-01-01\"}"));
        }
        MvcResult result = mvc.perform(get("/booking/getRoomsByDate/2019-01-01")
                .contentType("application/json")
                .accept("application/json")
        ).andReturn();
        Assert.assertEquals("{\"code\":400,\"message\":\"fail\",\"data\":null}",result.getResponse().getContentAsString());
    }

    //有房预定的情况
    @Test
    public void getRoomsByDateIfSuccess() throws Exception {
        for (int i = 0; i < 199; i++) {
            mvc.perform(post("/booking/add")
                    .contentType("application/json")
                    .content("{\"id\":\"0\",\"room\":" + i + ",\"guest\":\"Guest0\",\"date\":\"2019-01-01\"}"));
        }
        MvcResult result = mvc.perform(get("/booking/getRoomsByDate/2019-01-01")
                .contentType("application/json")
                .accept("application/json")
        ).andReturn();
        Assert.assertEquals("{\"code\":200,\"message\":\"success\",\"data\":[{\"roomNumber\":199,\"status\":0}]}",result.getResponse().getContentAsString());
    }

    //测试无预定的情况
    @Test
    public void getBookingByGuestIfNull() throws Exception {
        MvcResult result = mvc.perform(get("/booking/getBookingByGuest/John")
                .contentType("application/json")
                .accept("application/json")
        ).andReturn();
        Assert.assertEquals("{\"code\":201,\"message\":\"success,no info\",\"data\":null}",
                result.getResponse().getContentAsString());
    }

    //测试有预定的情况
    @Test
    public void getBookingByGuestIfExist() throws Exception {
        mvc.perform(post("/booking/add")
                .contentType("application/json")
                .content("{\"id\":\"1\",\"room\":12,\"guest\":\"John Doe\",\"date\":\"2022-12-12\"}"));
        MvcResult result = mvc.perform(get("/booking/getBookingByGuest/John Doe")
                .contentType("application/json")
                .accept("application/json")
        ).andReturn();
        Assert.assertEquals("{\"code\":200,\"message\":\"success\",\"data\":[{\"id\":1,\"room\":12,\"guest\":\"John " +
                        "Doe\",\"date\":\"2022-12-12\"}]}",
                result.getResponse().getContentAsString());
    }
}
