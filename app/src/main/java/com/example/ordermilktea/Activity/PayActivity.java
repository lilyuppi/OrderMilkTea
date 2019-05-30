package com.example.ordermilktea.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.example.ordermilktea.Adapter.PayAdapter;
import com.example.ordermilktea.Firebase.DataFireBase;
import com.example.ordermilktea.Firebase.DataStoreCallBack;
import com.example.ordermilktea.Model.Cart;
import com.example.ordermilktea.Model.HistoryModel;
import com.example.ordermilktea.Model.Information;
import com.example.ordermilktea.Model.MilkTeaInCart;
import com.example.ordermilktea.Model.Store;
import com.example.ordermilktea.R;
import com.example.ordermilktea.SharedPreferences.InformationLogin;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PayActivity extends AppCompatActivity implements DataStoreCallBack {
    ArrayList<MilkTeaInCart> list;
    private Dialog dialog;
    private PayAdapter payAdapter;
    private RecyclerView recyclerView;
    private ImageView imgPay, imvBackDrop;
    private Button btnBook, btnYes, btnNo;
    private int shipPrice = 15000;
    private TextView tvNumOfOrders, tvName, tvSugar, tvPrice, tvAllNumofOrders, tvSumPrice, tvSum;
    private Toolbar toolbar;
    private Information informationStore;
    private Cart cart;
    private DataFireBase dataFireBase;
    private String imgSrcStore;
    private String nameStore;
    private InformationLogin informationLogin;
    public static Activity activity;
    private FirebaseUser user;

    private static final int MY_RESQUEST_CODE = 1111;
    List<AuthUI.IdpConfig> providers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        dataFireBase = new DataFireBase(this, this);
        map();


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getData();
        recyclerView.setHasFixedSize(true);
    }

    private void getData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            nameStore = bundle.getString("name_store");
            getSupportActionBar().setTitle(nameStore);
            imgSrcStore = bundle.getString("img_store");
            cart = (Cart) bundle.getSerializable("cart");
            informationStore = (Information) bundle.getSerializable("information");
            list = new ArrayList<>();
            list = cart.getListMilkTeaInCart();
            int numberofOrders = 0;
            for (int i = 0; i < list.size(); i++) {
                numberofOrders += list.get(i).getNumberOfOrders();
            }
            tvAllNumofOrders.setText(numberofOrders + "");
            tvSumPrice.setText(cart.getSumPrice() + "");
            tvSum.setText(cart.getSumPrice() + shipPrice + "");

            imgPay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tvSum.setText(cart.getSumPrice() + "");
                }
            });

        }
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        payAdapter = new PayAdapter(list, getApplicationContext());
        recyclerView.setAdapter(payAdapter);

    }

    private void addCartToFireBase() {
        dataFireBase.setNewCart(informationStore, cart, imgSrcStore, nameStore);
    }

    private void map() {
        tvNumOfOrders = findViewById(R.id.tv_num_of_order);
        tvName = findViewById(R.id.tv_name);
        tvSugar = findViewById(R.id.tv_sugar_ice_topping);
        tvPrice = findViewById(R.id.money);
        tvAllNumofOrders = findViewById(R.id.tv_all_num_of_orders);
        tvSumPrice = findViewById(R.id.tv_sum_price);
        tvSum = findViewById(R.id.tv_sum_price_after);
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recyclerviewPay);
        imgPay = findViewById(R.id.airpay);
        btnBook = findViewById(R.id.buttonbook);
        btnYes = findViewById(R.id.button_yes);
        btnNo = findViewById(R.id.button_no);
        imvBackDrop = findViewById(R.id.imv_backdrop_pay);
        imvBackDrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PayActivity.this, MapsActivity.class);
                startActivityForResult(intent, 111);
            }
        });

        activity = this;
        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.main.finish();
                informationLogin = new InformationLogin(MainActivity.main);
                if (informationLogin.getIsLogined()) {
                    addCartToFireBase();
                    Intent intent = new Intent(PayActivity.this, SuccessActivity.class);
                    startActivity(intent);
                } else {
                    // chua dang nhap
                    Login();
                }
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

    @Override
    public void onReceiveHistory(ArrayList<HistoryModel> historyModelArrayList) {

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MY_RESQUEST_CODE) {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if (resultCode == RESULT_OK) {
                updateAfterLogin();
            }
            if (resultCode == RESULT_CANCELED) {
//                Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void updateAfterLogin() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        informationLogin.getPhoneNumberFromFireBase();
        informationLogin.setOnReceived(new InformationLogin.OnReceived() {
            @Override
            public void onReceivecPhone(String phone) {
                informationLogin.setSharedPre(true, user.getUid(), phone);
            }
        });
    }

    private void Login() {
        FacebookSdk.sdkInitialize(this);
        AppEventsLogger.activateApp(this);
        providers = Arrays.asList(
                new AuthUI.IdpConfig.GoogleBuilder().build(),
                new AuthUI.IdpConfig.FacebookBuilder().build()
        );
        showSignInOptions();
    }

    private void showSignInOptions() {
        startActivityForResult(
                AuthUI.getInstance().createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setTheme(R.style.AppTheme)
                        .build(), MY_RESQUEST_CODE
        );
    }
}
