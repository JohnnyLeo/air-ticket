package com.example.air.web;

import com.alibaba.fastjson.JSON;
import com.example.air.domain.AirTicket;
import com.example.air.domain.LowPrice;
import com.example.air.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping(value = "/Ticket")
public class TicketController {
    @Autowired
    TicketService ticketService;

    @RequestMapping(value = "/getTicket")
    public void getTicket(HttpServletResponse response, @RequestParam(value = "depatureCityName")String depatureCityName,
                          @RequestParam(value = "arrivalCityName")String arrivalCityName,@RequestParam(value = "departureDate")String departureDate) throws IOException{
        List<AirTicket> list = ticketService.queryDirTickets(depatureCityName,arrivalCityName,departureDate);
        String content = JSON.toJSONString(list);
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(content == null ? "" : content);

    }
    @RequestMapping(value = "/getLowestPrice")
    public void getLowestPrice(HttpServletResponse response,@RequestParam(value = "depatureCityName")String depatureCityName,
                               @RequestParam(value = "departureDate")String departureDate) throws  IOException{
        List<LowPrice> list = ticketService.queryLowestPrice(depatureCityName,departureDate);
        String content = JSON.toJSONString(list);
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(content == null ? "" : content);


    }
    @RequestMapping(value = "/getdeparture")
    public void getDeparture(HttpServletResponse response) throws  IOException{
        response.setContentType("text/json;charset=utf-8");
        String content = ticketService.getdepatureCityName();
        System.out.println(content);
        response.getWriter().write(content == null ? "" : content);
    }
    @RequestMapping(value = "/getArrival")
    public void getArrival(HttpServletResponse response) throws  IOException{
        response.setContentType("text/json;charset=utf-8");
        String content = ticketService.getarrivalCityName();
        System.out.println(content);
        response.getWriter().write(content == null ? "" : content);
    }
}

