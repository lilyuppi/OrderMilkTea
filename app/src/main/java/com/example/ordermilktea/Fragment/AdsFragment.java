package com.example.ordermilktea.Fragment;


import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ordermilktea.Model.Store;
import com.example.ordermilktea.Adapter.AdsAdapter;
import com.example.ordermilktea.R;

import java.util.ArrayList;


public class AdsFragment extends Fragment {

    ArrayList<String> listBanner = new ArrayList<>();
    ArrayList<Store> mListStore = new ArrayList<>();
    View view;
    ViewPager viewPagerbanner;
    TabLayout tabLayoutbanner;
    //CircleIndicator circleIndicator;
    // AdsAdapter quangCaoAdapter;

    Image img;

    Runnable runnable;
    Handler handler;


    int currentTime;
    @Override
    public View onCreateView(@NonNull  LayoutInflater inflater,@Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_ads, container, false);

        Log.d("print", "onCreateView: ");
        anhxa();
        init();
        return view;
    }

    private void init() {
        mListStore = (ArrayList<Store>) getArguments().getSerializable("store");
        AdsAdapter adsAdapter = new AdsAdapter(getFragmentManager());
        for (int i = 0; i < 3; i++){
            Banner1Fragment banner1Fragment = new Banner1Fragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("store", mListStore.get(i));
            banner1Fragment.setArguments(bundle);
            adsAdapter.addFragment(banner1Fragment, "1");
        }
        handler=new Handler();
        runnable=new Runnable() {
            @Override
            public void run() {
                currentTime=viewPagerbanner.getCurrentItem();
                currentTime++;
                if(currentTime >= viewPagerbanner.getAdapter().getCount()){
                    currentTime = 0 ;
                }
                viewPagerbanner.setCurrentItem(currentTime,true);
                handler.postDelayed(runnable,3000);
            }
        };
        handler.postDelayed(runnable,3000);
        viewPagerbanner.setAdapter(adsAdapter);
        tabLayoutbanner.setupWithViewPager(viewPagerbanner);
    }



    private void anhxa() {
        viewPagerbanner = view.findViewById(R.id.myViewPagerbanner);
        tabLayoutbanner = view.findViewById(R.id.tabLayout);
    }


}
