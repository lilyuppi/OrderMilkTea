package com.example.ordermilktea.Firebase;

import android.widget.ArrayAdapter;

import com.example.ordermilktea.Model.HistoryModel;
import com.example.ordermilktea.Model.Store;

import java.util.ArrayList;

public interface DataStoreCallBack {
    void onReceiveListStore(ArrayList<Store> listStore);

    void onReceiveHistory(ArrayList<HistoryModel> historyModelArrayList);
}
