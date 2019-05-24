package com.example.ordermilktea.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ordermilktea.Activity.StoreActivity;
import com.example.ordermilktea.Adapter.SuggestAdapter;
import com.example.ordermilktea.Model.Store;
import com.example.ordermilktea.R;

import java.util.ArrayList;

public class FavoriteFragment extends Fragment {

    View view;

    RecyclerView recyclerView;
    SuggestAdapter suggestAdapter;

    ArrayList<Store> mListStore = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_favorite, container, false);
        recyclerView=view.findViewById(R.id.recyclerviewFavorite);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity().getApplicationContext(), 3,LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        mListStore = (ArrayList<Store>) getArguments().getSerializable("store");
        ArrayList<Store> list = new ArrayList<>();
        for (int i = 0; i < mListStore.size(); i++) {
            list.add(mListStore.get(i));
            if (i == 11) {
                break;
            }
        }
        suggestAdapter = new SuggestAdapter(getActivity().getApplicationContext(), list);
        recyclerView.setAdapter(suggestAdapter);
        suggestAdapter.setOnItemClickedListener(new SuggestAdapter.OnItemClickedListener() {
            @Override
            public void onItemClick(Store store) {
                Intent intent = new Intent(getActivity().getApplicationContext(), StoreActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("store", store);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        return view;
    }



//    @Override
//    public void onReceiveListStore(ArrayList<Store> listStore) {
//        suggestAdapter = new SuggestAdapter(getActivity().getApplicationContext(),list);
//        recyclerView.setAdapter(suggestAdapter);
//    }
}

