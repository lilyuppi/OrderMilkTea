package com.example.ordermilktea.Fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ordermilktea.Model.Store;
import com.example.ordermilktea.R;

import java.util.ArrayList;

public class LoveFragment extends Fragment {

    View view;
    ArrayList<Store> listStore;
    @Override
    public View onCreateView(@NonNull  LayoutInflater inflater,@Nullable ViewGroup container,
                             @Nullable  Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_love, container, false);

        listStore = (ArrayList<Store>) getArguments().getSerializable("store");

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Fragment mostOrderFragment = new MostOrderFragment();
        Fragment lovedFragment = new LovedFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("store", listStore);
        mostOrderFragment.setArguments(bundle);
        lovedFragment.setArguments(bundle);
        fragmentTransaction.add(R.id.fragment_most_order,mostOrderFragment);
        fragmentTransaction.add(R.id.fragment_blank_fragment2,lovedFragment);
        fragmentTransaction.commit();
        return view;
    }

}
