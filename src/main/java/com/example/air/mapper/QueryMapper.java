package com.example.air.mapper;

import com.example.air.domain.AirTicket;
import com.example.air.domain.LowPrice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QueryMapper {
    //根据出发到达地和出发时间查询航班信息
    @Select("select * from table " +
            "where depatureCityName = #{depatureCityName} and arrivalCityName = #{arrivalCityName} and departureDate = #{departureDate}" +
            "order by 'order'")
    List<AirTicket> queryDirTickets(String depatureCityName,String arrivalCityName,String departureDate);
    //根据出发地直达和时间查询最低价
    @Select("select  arrivalCityName,price from directLowest where depatureCityName = #{depatureCityName} and departureDate = #{departureDate}")
    List<LowPrice> queryDirLowestPrice(String depatureCityName, String departureDate);
    //从一次中转表根据出发地和时间查询最低价
    @Select("select  arrivalCityName,price from firstLowest where depatureCityName = #{depatureCityName} and departureDate = #{departureDate}")
    List<LowPrice> queryfirLowestPrice(String depatureCityName,String departureDate);
    //从二次中转表根据出发地和时间查询最低价
    @Select("select  arrivalCityName,price from secondLowest where depatureCityName = #{depatureCityName} and departureDate = #{departureDate}")
    List<LowPrice> querysecLowestPrice(String depatureCityName,String departureDate);
}
