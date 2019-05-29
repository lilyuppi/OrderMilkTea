package com.example.ordermilktea.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.ordermilktea.R;

import java.util.ArrayList;

public class AdsAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> arrayFragment = new ArrayList<>();
    private ArrayList<String> arrayTittle = new ArrayList<>();

    public AdsAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return arrayFragment.get(i);
    }

    @Override
    public int getCount() {
        return arrayFragment.size();
    }

    public void addFragment(Fragment fragment, String tittle) {
        arrayFragment.add(fragment);
        arrayTittle.add(tittle);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return arrayTittle.get(position);
    }
}
