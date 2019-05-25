package com.example.ordermilktea.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.ordermilktea.Firebase.DataStoreCallBack;
import com.example.ordermilktea.Model.Store;
import com.example.ordermilktea.R;

import java.util.ArrayList;

public class TrangChuFragment extends Fragment {

    View view;
    ArrayList<Store> listStore;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,
                             @Nullable  Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =inflater.inflate(R.layout.fragment_trang_chu, container, false);

        listStore = (ArrayList<Store>) getArguments().getSerializable("store");

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragmentQuangCao = new QuangCaoFragment();
        Fragment fragmentSuggest = new SuggestFragment();
        Fragment fragmentFavorite = new FavoriteFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("store", listStore);
        fragmentQuangCao.setArguments(bundle);
        fragmentSuggest.setArguments(bundle);
        fragmentFavorite.setArguments(bundle);
        fragmentTransaction.add(R.id.fragment_quang_cao, fragmentQuangCao);
        fragmentTransaction.add(R.id.fragment_suggest, fragmentSuggest);
        fragmentTransaction.add(R.id.fragment_favorite, fragmentFavorite);
        fragmentTransaction.commit();
        return view;
    }


}
