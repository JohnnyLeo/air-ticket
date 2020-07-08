package com.example.air.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author jleo
 * @date 2020/7/8
 */
@SpringBootTest
class TicketServiceTest {

    @Autowired
    TicketService ticketService;
    @Test
    void queryDirTickets() {
        System.out.println(ticketService.queryDirTickets("深圳", "南昌", "2020-07-06"));
    }
}