package com.example.air.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author jleo
 * @date 2020/7/8
 */
@SpringBootTest
class TicketServiceTest {

    @Test
    void queryDirTickets() {
        new TicketService().queryDirTickets("阿勒泰", "和田", "2020-07-06");
    }
}