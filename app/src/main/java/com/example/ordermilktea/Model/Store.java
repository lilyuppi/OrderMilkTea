package com.example.ordermilktea.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class Store implements Serializable {
    private String name;
    private Information information;
    private String imgSrc;
    private int discount;
    private int numberOfOrders;
    private boolean isFreeShip;
    private boolean isAirPay;
    private ArrayList<MilkTea> listMilkTea;

    public Store(String name, Information information, String imgSrc, int discount, boolean isFreeShip, boolean isAirPay, ArrayList<MilkTea> listMilkTea) {
        this.name = name;
        this.information = information;
        this.imgSrc = imgSrc;
        this.discount = discount;
        this.isFreeShip = isFreeShip;
        this.isAirPay = isAirPay;
        this.listMilkTea = listMilkTea;
    }

    public Store() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Information getInformation() {
        return information;
    }

    public void setInformation(Information information) {
        this.information = information;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public boolean isFreeShip() {
        return isFreeShip;
    }

    public void setFreeShip(boolean freeShip) {
        isFreeShip = freeShip;
    }

    public boolean isAirPay() {
        return isAirPay;
    }

    public void setAirPay(boolean airPay) {
        isAirPay = airPay;
    }

    public int getNumberOfOrders() {
        return numberOfOrders;
    }

    public void setNumberOfOrders(int numberOfOrders) {
        this.numberOfOrders = numberOfOrders;
    }

    public ArrayList<MilkTea> getListMilkTea() {
        return listMilkTea;
    }

    public void setListMilkTea(ArrayList<MilkTea> listMilkTea) {
        this.listMilkTea = listMilkTea;
    }
}
