package com.example.ordermilktea.Activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ordermilktea.Adapter.PayAdapter;
import com.example.ordermilktea.Adapter.SuggestAdapter;
import com.example.ordermilktea.Dialog.BottomSheetDialogShowCart;
import com.example.ordermilktea.Firebase.DataFireBase;
import com.example.ordermilktea.Firebase.DataStoreCallBack;
import com.example.ordermilktea.Fragment.BlankFragment;
import com.example.ordermilktea.Fragment.TrangChuFragment;
import com.example.ordermilktea.Model.Cart;
import com.example.ordermilktea.Model.Information;
import com.example.ordermilktea.Model.MilkTeaInCart;
import com.example.ordermilktea.Model.Store;
import com.example.ordermilktea.R;

import java.util.ArrayList;
import java.util.List;

public class PayActivity extends AppCompatActivity implements DataStoreCallBack {
    ArrayList<MilkTeaInCart> list;
    private Dialog dialog;
    private PayAdapter payAdapter;
    private RecyclerView recyclerView;
    private CardView imgpay,directpay;
    private Button btnbook,btnyes,btnno;
    private int ship = 15000;
    private TextView soluongsp,tensp,sugar,money,tongsoluong,tongtien,sum;
    private Toolbar toolbar;
    private Information informationStore;
    private Cart cart;
    private DataFireBase dataFireBase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        dataFireBase = new DataFireBase( this);
        map();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getData();
        recyclerView.setHasFixedSize(true);
    }

    private void getData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            final Cart cart;
            cart = (Cart) bundle.getSerializable("cart");
            informationStore = (Information) bundle.getSerializable("information");
            list = new ArrayList<>();
            list= cart.getListMilkTeaInCart();
            int numberofOrders = 0;
            for (int i = 0;i<list.size();i++){
                numberofOrders+=list.get(i).getNumberOfOrders();
            }
            tongsoluong.setText(numberofOrders+"");
            tongtien.setText(cart.getSumPrice()+"");
            sum.setText(cart.getSumPrice()+ship+"");

            imgpay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sum.setText(cart.getSumPrice()+"");
                }
            });

            directpay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sum.setText(cart.getSumPrice()+ship+"");
                }
            });

        }
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,  LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

        payAdapter = new PayAdapter(list,getApplicationContext());
        recyclerView.setAdapter(payAdapter);

    }

    private void addCartToFireBase(){
//        dataFireBase.setNewCart(informationStore, cart);
    }

    private void map() {
        soluongsp=findViewById(R.id.so_luong_san_pham);
        tensp=findViewById(R.id.ten_san_pham);
        sugar=findViewById(R.id.sugar_ice_topping);
        money=findViewById(R.id.money);
        tongsoluong=findViewById(R.id.tong_so_luong);
        tongtien=findViewById(R.id.tong_tien_tra);
        sum=findViewById(R.id.sum_tien);
        toolbar=findViewById(R.id.toolbar);
        recyclerView=findViewById(R.id.recyclerviewPay);
        imgpay=findViewById(R.id.airpay);
        directpay=findViewById(R.id.directpay);
        btnbook=findViewById(R.id.buttonbook);
        btnyes=findViewById(R.id.button_yes);
        btnno=findViewById(R.id.button_no);



        btnbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCartToFireBase();
                Intent intent=new Intent(PayActivity.this,SuccessActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    @Override
    public void onReceiveListStore(ArrayList<Store> listStore) {

    }
}
