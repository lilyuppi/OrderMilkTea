package com.example.ordermilktea.Model;

import java.io.Serializable;

public class Information implements Serializable {
    private String address;
    private int phoneNumber;

    public Information(String address, int phoneNumber) {
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
