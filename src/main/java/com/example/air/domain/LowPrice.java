package com.example.air.domain;

public class LowPrice {
    String arrivalCityName;
    Integer price;

    @Override
    public String toString() {
        return "LowPrice{" +
                "arrivalCityName='" + arrivalCityName + '\'' +
                ", price=" + price +
                '}';
    }

    public String getArrivalCityName() {
        return arrivalCityName;
    }

    public void setArrivalCityName(String arrivalCityName) {
        this.arrivalCityName = arrivalCityName;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public LowPrice(String arrivalCityName, Integer price) {
        this.arrivalCityName = arrivalCityName;
        this.price = price;
    }

    public LowPrice() {
    }
}
