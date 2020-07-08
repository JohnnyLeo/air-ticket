package com.example.air.domain;

public class AirTicket {
    String airlineName;//航空公司名称
    String flightNumber;//航班号
    String craftTypeName;//搭乘飞机款式
    String departureDate;//出发日期
    String departureTime;//出发时间
    String arrivalDate;//到达日期
    String arrivalTime;//到达时间
    String departureCityTlc;//出发地城市名缩写
    String depatureCityName;//出发地城市名
    String departureAirportTlc;//出发地机场缩写
    String departureAirportName;//出发地机场名
    String departureAirportTerminal;//出发地机场航站楼
    String arrivalCityTlc;//目的地城市缩写
    String arrivalCityName;//目的地城市名
    String arrivalAirportTlc;//目的地机场名缩写
    String arrivalAirportName;//目的地机场名
    String arrivalAirportTerminal;//目的地机场航站楼
    Integer price;//机票价格
    String triptime;//旅途间隔

    public String getAirlineName() {
        return airlineName;
    }

    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getCraftTypeName() {
        return craftTypeName;
    }

    public void setCraftTypeName(String craftTypeName) {
        this.craftTypeName = craftTypeName;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(String arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getDepartureCityTlc() {
        return departureCityTlc;
    }

    public void setDepartureCityTlc(String departureCityTlc) {
        this.departureCityTlc = departureCityTlc;
    }

    public String getDepatureCityName() {
        return depatureCityName;
    }

    public void setDepatureCityName(String depatureCityName) {
        this.depatureCityName = depatureCityName;
    }

    public String getDepartureAirportTlc() {
        return departureAirportTlc;
    }

    public void setDepartureAirportTlc(String departureAirportTlc) {
        this.departureAirportTlc = departureAirportTlc;
    }

    public String getDepartureAirportName() {
        return departureAirportName;
    }

    public void setDepartureAirportName(String departureAirportName) {
        this.departureAirportName = departureAirportName;
    }

    public String getDepartureAirportTerminal() {
        return departureAirportTerminal;
    }

    public void setDepartureAirportTerminal(String departureAirportTerminal) {
        this.departureAirportTerminal = departureAirportTerminal;
    }

    public String getArrivalCityTlc() {
        return arrivalCityTlc;
    }

    public void setArrivalCityTlc(String arrivalCityTlc) {
        this.arrivalCityTlc = arrivalCityTlc;
    }

    public String getArrivalCityName() {
        return arrivalCityName;
    }

    public void setArrivalCityName(String arrivalCityName) {
        this.arrivalCityName = arrivalCityName;
    }

    public String getArrivalAirportTlc() {
        return arrivalAirportTlc;
    }

    public void setArrivalAirportTlc(String arrivalAirportTlc) {
        this.arrivalAirportTlc = arrivalAirportTlc;
    }

    public String getArrivalAirportName() {
        return arrivalAirportName;
    }

    public void setArrivalAirportName(String arrivalAirportName) {
        this.arrivalAirportName = arrivalAirportName;
    }

    public String getArrivalAirportTerminal() {
        return arrivalAirportTerminal;
    }

    public void setArrivalAirportTerminal(String arrivalAirportTerminal) {
        this.arrivalAirportTerminal = arrivalAirportTerminal;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getTriptime() {
        return triptime;
    }

    public void setTriptime(String triptime) {
        this.triptime = triptime;
    }

    public AirTicket() {
    }

    public AirTicket(String airlineName, String flightNumber, String craftTypeName, String departureDate, String departureTime, String arrivalDate, String arrivalTime, String departureCityTlc, String depatureCityName, String departureAirportTlc, String departureAirportName, String departureAirportTerminal, String arrivalCityTlc, String arrivalCityName, String arrivalAirportTlc, String arrivalAirportName, String arrivalAirportTerminal, Integer price, String triptime) {
        this.airlineName = airlineName;
        this.flightNumber = flightNumber;
        this.craftTypeName = craftTypeName;
        this.departureDate = departureDate;
        this.departureTime = departureTime;
        this.arrivalDate = arrivalDate;
        this.arrivalTime = arrivalTime;
        this.departureCityTlc = departureCityTlc;
        this.depatureCityName = depatureCityName;
        this.departureAirportTlc = departureAirportTlc;
        this.departureAirportName = departureAirportName;
        this.departureAirportTerminal = departureAirportTerminal;
        this.arrivalCityTlc = arrivalCityTlc;
        this.arrivalCityName = arrivalCityName;
        this.arrivalAirportTlc = arrivalAirportTlc;
        this.arrivalAirportName = arrivalAirportName;
        this.arrivalAirportTerminal = arrivalAirportTerminal;
        this.price = price;
        this.triptime = triptime;
    }
}
