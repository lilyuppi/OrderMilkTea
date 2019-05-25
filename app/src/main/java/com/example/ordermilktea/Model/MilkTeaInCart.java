package com.example.ordermilktea.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class MilkTeaInCart implements Serializable {
    private String name;
    private String sugar;
    private String ice;
    private int numberOfOrders;
    private ArrayList<String> topping;
    private int price;
    private int priceTopping;
    public MilkTeaInCart() {
        this.priceTopping = 5000;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getSugar() {
        return sugar;
    }

    public void setSugar(String sugar) {
        this.sugar = sugar;
    }

    public String getIce() {
        return ice;
    }

    public void setIce(String ice) {
        this.ice = ice;
    }

    public ArrayList<String> getTopping() {
        return topping;
    }

    public void setTopping(ArrayList<String> topping) {
        this.topping = topping;
    }

    public int getPriceTopping() {
        return priceTopping;
    }

    public void setPriceTopping(int priceTopping) {
        this.priceTopping = priceTopping;
    }
}
