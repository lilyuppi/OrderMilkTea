package com.example.ordermilktea.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ordermilktea.Adapter.MilkTeaAdapter;
import com.example.ordermilktea.Dialog.BottomSheetDialogAddToCart;
import com.example.ordermilktea.Dialog.BottomSheetDialogShowCart;
import com.example.ordermilktea.Model.Cart;
import com.example.ordermilktea.Model.MilkTea;
import com.example.ordermilktea.Model.MilkTeaInCart;
import com.example.ordermilktea.Model.Store;
import com.example.ordermilktea.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class StoreActivity extends AppCompatActivity {
    private ImageView imvBackDrop;
    private TextView tvTest, tvSumPrice, tvSumItem;
    private ArrayList<MilkTea> listMilkTea;
    private Toolbar toolbar;
    private View viewShowCart, tvThanhToan;
    private Cart mCart;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        initCart();
        map();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getBundle();
    }


    private void initCart() {
        mCart = new Cart();
    }

    private void getBundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Store store = (Store) bundle.getSerializable("store");
            getSupportActionBar().setTitle(store.getName());
            toolbar.setSubtitle(store.getInformation().getAddress());
            tvTest.setText(store.getName());
            Glide.with(this).load(store.getImgSrc()).into(imvBackDrop);
            listMilkTea = store.getListMilkTea();
            showListMilkTea();
        }
    }

    private void removeBundle(){
        getIntent().removeExtra("store");
    }

    private void showListMilkTea() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view_milk_tea);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        MilkTeaAdapter milkTeaAdapter = new MilkTeaAdapter(listMilkTea, getApplicationContext());
        recyclerView.setAdapter(milkTeaAdapter);

        milkTeaAdapter.setOnItemClickedListener(new MilkTeaAdapter.OnItemClickedListener() {
            @Override
            public void onItemClick(MilkTea milkTea) {
                BottomSheetDialogAddToCart bottomSheetDialogAddToCart = new BottomSheetDialogAddToCart();
                Bundle bundle = new Bundle();
                bundle.putSerializable("milktea", milkTea);
                bottomSheetDialogAddToCart.setArguments(bundle);
                bottomSheetDialogAddToCart.show(getSupportFragmentManager(), "addBottomSheet");
                bottomSheetDialogAddToCart.setOnReceivedToppingListener(new BottomSheetDialogAddToCart.OnToppingSelectedListener() {
                    @Override
                    public void onToppingSelected(MilkTeaInCart milkTeaInCart) {
                        ArrayList<MilkTeaInCart> listMilTeaInCart = mCart.getListMilkTeaInCart();
                        if (listMilTeaInCart == null) {
                            listMilTeaInCart = new ArrayList<>();
                        }
                        listMilTeaInCart.add(milkTeaInCart);
                        mCart.setListMilkTeaInCart(listMilTeaInCart);
                        reloadCart();
                    }

                });

            }

        });

    }
    private void reloadCart(){
        int sumItem = 0;
        mCart.calSumPrice();
        for (MilkTeaInCart milkTeaInCart : mCart.getListMilkTeaInCart()) {
            sumItem += milkTeaInCart.getNumberOfOrders();
        }
        if (sumItem == 0) {
            viewShowCart.setVisibility(View.GONE);
        }else {
            viewShowCart.setVisibility(View.VISIBLE);
            tvSumItem.setText(sumItem + " món");
            String str = NumberFormat.getNumberInstance(Locale.US).format(mCart.getSumPrice());
            tvSumPrice.setText(str + "đ");

        }
    }
    private void map() {
        imvBackDrop = findViewById(R.id.imv_backdrop);
        tvTest = findViewById(R.id.tv_test);
        tvSumPrice = findViewById(R.id.tv_sum_price_in_show_cart);
        tvSumItem = findViewById(R.id.tv_sum_item_milk_tea_in_cart);
        toolbar = findViewById(R.id.toolbar);
        tvThanhToan=findViewById(R.id.thanhtoan);
        viewShowCart = findViewById(R.id.linearlayout_show_cart);
        viewShowCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialogShowCart bottomSheetDialogShowCart = new BottomSheetDialogShowCart();
                Bundle bundle = new Bundle();
                bundle.putSerializable("cart", mCart);
                bottomSheetDialogShowCart.setArguments(bundle);
                bottomSheetDialogShowCart.show(getSupportFragmentManager(), "cartBottomSheet");
                bottomSheetDialogShowCart.setOnCartChangedListener(new BottomSheetDialogShowCart.OnCartChangedListener() {
                    @Override
                    public void onCartChanged(Cart cart) {
                        mCart = cart;
                        reloadCart();
                    }

                    @Override
                    public void onCancel(Cart cart) {
                        mCart = cart;
                        reloadCart();
                    }
                });
            }
        });
        tvThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(StoreActivity.this,PayActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("cart", mCart);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        removeBundle();
        overridePendingTransition(R.anim.anim_slide_in_from_left, R.anim.anim_slide_out_to_right);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
