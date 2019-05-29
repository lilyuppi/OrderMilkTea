package com.example.ordermilktea.Model;

public class HistoryModel {
    private Cart cart;
    private Information informationStore;
    private String timeOrder;
    private User user;
    private String imgStore;
    private String nameStore;

    public HistoryModel() {
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Information getInformationStore() {
        return informationStore;
    }

    public void setInformationStore(Information informationStore) {
        this.informationStore = informationStore;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTimeOrder() {
        return timeOrder;
    }

    public void setTimeOrder(String timeOrder) {
        this.timeOrder = timeOrder;
    }

    public String getImgStore() {
        return imgStore;
    }

    public void setImgStore(String imgStore) {
        this.imgStore = imgStore;
    }

    public String getNameStore() {
        return nameStore;
    }

    public void setNameStore(String nameStore) {
        this.nameStore = nameStore;
    }
}
