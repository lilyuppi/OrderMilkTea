package com.example.ordermilktea.Firebase;

import com.example.ordermilktea.Model.Store;

import java.util.ArrayList;

public interface DataStoreCallBack {
    void onReceiveListStore(ArrayList<Store> listStore);
}
