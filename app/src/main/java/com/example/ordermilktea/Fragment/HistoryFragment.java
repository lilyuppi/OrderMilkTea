package com.example.ordermilktea.Fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ordermilktea.Adapter.HistoryAdapter;
import com.example.ordermilktea.Firebase.DataFireBase;
import com.example.ordermilktea.Firebase.DataStoreCallBack;
import com.example.ordermilktea.Model.HistoryModel;
import com.example.ordermilktea.Model.Store;
import com.example.ordermilktea.R;

import java.util.ArrayList;

public class HistoryFragment extends Fragment implements DataStoreCallBack {
    View view;
    RecyclerView recyclerView;
    HistoryAdapter historyAdapter;
    DataFireBase dataFireBase;

    @Override
    public View onCreateView(@NonNull  LayoutInflater inflater,@Nullable ViewGroup container,
                             @Nullable  Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_history, container, false);
        return view;
    }

    @Override
    public void onReceiveListStore(ArrayList<Store> listStore) {
    }

    @Override
    public void onReceiveHistory(ArrayList<HistoryModel> historyModelArrayList) {
        recyclerView = view.findViewById(R.id.recycler_view_history);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        historyAdapter = new HistoryAdapter(historyModelArrayList, getActivity().getApplicationContext());
        recyclerView.setAdapter(historyAdapter);
    }

    @Override
    public void onResume() {
        dataFireBase = new DataFireBase(this, getActivity());
        dataFireBase.getHistory();
        super.onResume();
    }
}
