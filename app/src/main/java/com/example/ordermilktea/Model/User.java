package com.example.ordermilktea.Model;

import java.io.Serializable;

public class User implements Serializable {
    private String name;
    private Information information;

    public User() {
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
}
