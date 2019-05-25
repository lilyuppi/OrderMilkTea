package com.example.ordermilktea.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class Cart implements Serializable, Cloneable {
    private ArrayList<MilkTeaInCart> listMilkTeaInCart;
    private int sumPrice;
    public Cart() {
        this.listMilkTeaInCart = new ArrayList<>();
    }

    public ArrayList<MilkTeaInCart> getListMilkTeaInCart() {
        return listMilkTeaInCart;
    }

    public void setListMilkTeaInCart(ArrayList<MilkTeaInCart> listMilkTeaInCart) {
        this.listMilkTeaInCart = listMilkTeaInCart;
    }

    public int getSumPrice() {
        return sumPrice;
    }

    public void setSumPrice(int sumPrice) {
        this.sumPrice = sumPrice;
    }

    public void calSumPrice(){
        sumPrice = 0;
        for (MilkTeaInCart milkTeaInCart : listMilkTeaInCart) {
            int price = milkTeaInCart.getPrice() * milkTeaInCart.getNumberOfOrders();
            sumPrice += price;
        }
    }
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
