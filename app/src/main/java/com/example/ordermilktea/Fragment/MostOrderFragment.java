package com.example.ordermilktea.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ordermilktea.Adapter.OrderAdapter;
import com.example.ordermilktea.Model.Store;
import com.example.ordermilktea.R;
import com.example.ordermilktea.Activity.StoreActivity;

import java.util.ArrayList;


public class MostOrderFragment extends Fragment {
    View view;
    RecyclerView recyclerView;
    OrderAdapter orderAdapter;

    ArrayList<Store> mListStore = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_most_order, container, false);
        recyclerView=view.findViewById(R.id.recyclerviewOrder);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        mListStore = (ArrayList<Store>) getArguments().getSerializable("store");

        /**
         *     Chi them 3 cai bat ky
         */
        ArrayList<Store> list=new ArrayList<>();
        for (int i = 0 ;i<mListStore.size();i++){
            list.add(mListStore.get(i));
            if (i==10){
                break;
            }
        }
        orderAdapter = new OrderAdapter(getActivity().getApplicationContext(), list);
        recyclerView.setAdapter(orderAdapter);
        orderAdapter.setOnItemClickedListener(new OrderAdapter.OnItemClickedListener() {
            @Override
            public void onItemClick(Store store) {
                Intent intent = new Intent(getActivity().getApplicationContext(), StoreActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("store", store);
                intent.putExtras(bundle);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.anim_slide_in_from_right, R.anim.anim_slide_out_to_left);
            }
        });
        return view;
    }

}
