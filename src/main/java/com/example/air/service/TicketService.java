package com.example.air.service;

import com.alibaba.fastjson.JSON;
import com.example.air.domain.AirTicket;
import com.example.air.domain.LowPrice;
import com.example.air.mapper.QueryMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class TicketService {
    @Resource
    QueryMapper queryMapper;

    public String getdepatureCityName(){
        return JSON.toJSONString(queryMapper.getdepatureCityName());
    }
    public String getarrivalCityName(){
        return JSON.toJSONString(queryMapper.getarrivalCityName());
    }

    public List<AirTicket> queryDirTickets(String depatureCityName, String arrivalCityName, String departureDate){
        return queryMapper.queryDirTickets(depatureCityName, arrivalCityName, departureDate);
    };
    //查询最低价机票
    public List<LowPrice> queryLowestPrice(String depatureCityName,String departureDate){
        List<LowPrice> list1 = queryMapper.queryDirLowestPrice(depatureCityName, departureDate);
//        List<LowPrice> list2 = queryMapper.queryDirLowestPrice(depatureCityName, departureDate);
//        List<LowPrice> list3 = queryMapper.queryDirLowestPrice(depatureCityName, departureDate);
//        List<LowPrice> list =  new ArrayList<>();
//
//        for (int i = 0;i < list1.size();i++){
//            LowPrice lowPrice = list1.get(i);
//            for (int j = 0;j < list2.size();j++){
//                if (list2.get(j).getArrivalCityName().equals(lowPrice.getArrivalCityName())){
//                    if (list2.get(j).getPrice() < lowPrice.getPrice()){
//                       lowPrice = list2.get(j);
//                    }
//                    break;
//                }
//            }
//            for (int j = 0;j < list3.size();j++){
//                if (list3.get(j).getArrivalCityName().equals(lowPrice.getArrivalCityName())){
//                    if (list3.get(j).getPrice() < lowPrice.getPrice()){
//                        lowPrice = list3.get(j);
//                    }
//                    break;
//                }
//            }
//            list.add(lowPrice);
//        }


        return list1;
    };

}
