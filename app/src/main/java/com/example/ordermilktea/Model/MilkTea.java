package com.example.ordermilktea.Model;

import java.io.Serializable;

public class MilkTea implements Serializable {
    private String name;
    private String describe;
    private String imgSrc;
    private int price;
    private int numberOfOrders;

    public MilkTea() {
    }

    public MilkTea(String name, String describe, String imgSrc, int price, int numberOfOrders) {
        this.name = name;
        this.describe = describe;
        this.imgSrc = imgSrc;
        this.price = price;
        this.numberOfOrders = numberOfOrders;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getNumberOfOrders() {
        return numberOfOrders;
    }

    public void setNumberOfOrders(int numberOfOrders) {
        this.numberOfOrders = numberOfOrders;
    }
}
