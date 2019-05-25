package com.example.ordermilktea.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ordermilktea.Model.Store;
import com.example.ordermilktea.R;

public class Banner1Fragment extends Fragment {
    View view;
    TextView tv;
    ImageView imv_banner;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_banner1, container, false);
        tv = view.findViewById(R.id.title_quang_cao);
        imv_banner = view.findViewById(R.id.imv_banner);

//        String src_banner = getArguments().getString("key");

        Store store = (Store) getArguments().getSerializable("store");
        if (store != null) {
            String src_banner = store.getImgSrc();
            Glide.with(getContext()).load(src_banner).into(imv_banner);

        }
        return view;
    }

}
