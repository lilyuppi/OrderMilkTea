package com.example.ordermilktea.Activity;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.example.ordermilktea.Adapter.MainViewPagerAdapter;
import com.example.ordermilktea.Firebase.DataFireBase;
import com.example.ordermilktea.Firebase.DataStoreCallBack;
import com.example.ordermilktea.Fragment.HistoryFragment;
import com.example.ordermilktea.Fragment.AboutFragment;
import com.example.ordermilktea.Fragment.LoveFragment;
import com.example.ordermilktea.Fragment.HomeFragment;
import com.example.ordermilktea.Model.HistoryModel;
import com.example.ordermilktea.Model.Store;
import com.example.ordermilktea.R;
import com.github.ybq.android.spinkit.SpinKitView;
import com.github.ybq.android.spinkit.SpriteFactory;
import com.github.ybq.android.spinkit.Style;
import com.github.ybq.android.spinkit.sprite.Sprite;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements DataStoreCallBack {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private View decorView;
    private SpinKitView spinKitView;
    private LinearLayout linearLayoutLoading;
    DataFireBase dataFireBase;
    public static Activity main;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        main = this;
        map();
        initLoading();
        dataFireBase = new DataFireBase(this, this);
        printhashkey();
    }

    private void initLoading() {
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        Style style = Style.values()[6];
        Sprite drawable = SpriteFactory.create(style);
        spinKitView.setIndeterminateDrawable(drawable);
    }

    private void map() {
        spinKitView = findViewById(R.id.spin_kit);
        linearLayoutLoading = findViewById(R.id.linearlayout_loading_main_activity);
        tabLayout = findViewById(R.id.myTabLayout);
        viewPager = findViewById(R.id.myViewPager);
        decorView = getWindow().getDecorView();
    }

    @Override
    public void onReceiveListStore(ArrayList<Store> listStore) {
        linearLayoutLoading.setVisibility(View.GONE);
        int uiOptions = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        decorView.setSystemUiVisibility(uiOptions);
        MainViewPagerAdapter mainViewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager());
        HomeFragment homeFragment = new HomeFragment();
        LoveFragment loveFragment = new LoveFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("store", listStore);
        homeFragment.setArguments(bundle);
        loveFragment.setArguments(bundle);
        mainViewPagerAdapter.addFragment(homeFragment, "");
        mainViewPagerAdapter.addFragment(new HistoryFragment(), "");
        mainViewPagerAdapter.addFragment(loveFragment, "");
        mainViewPagerAdapter.addFragment(new AboutFragment(), "");
        viewPager.setAdapter(mainViewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.baseline_home_black_18dp);
        tabLayout.getTabAt(1).setIcon(R.drawable.baseline_assignment_black_18dp);
        tabLayout.getTabAt(2).setIcon(R.drawable.baseline_favorite_black_18dp);
        tabLayout.getTabAt(3).setIcon(R.drawable.baseline_person_black_18dp);

    }

    @Override
    public void onReceiveHistory(ArrayList<HistoryModel> historyModelArrayList) {

    }

    public void printhashkey() {

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.example.ordermilktea",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }
}
