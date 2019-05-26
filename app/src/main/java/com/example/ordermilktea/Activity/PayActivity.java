package com.example.ordermilktea.Activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.example.ordermilktea.Fragment.BlankFragment;
import com.example.ordermilktea.Fragment.TrangChuFragment;
import com.example.ordermilktea.Model.Cart;
import com.example.ordermilktea.Model.MilkTeaInCart;
import com.example.ordermilktea.Model.Store;
import com.example.ordermilktea.R;

import java.util.ArrayList;
import java.util.List;

public class PayActivity extends AppCompatActivity {
    ArrayList<MilkTeaInCart> list;
    private Dialog dialog;
    PayAdapter payAdapter;
    RecyclerView recyclerView;
    ImageView imgpay;
    Button btnbook,btnyes,btnno;
    int ship = 15000;
    TextView soluongsp,tensp,sugar,money,tongsoluong,tongtien,sum;
    Toolbar toolbar;
    BottomSheetDialogShowCart bottomSheetDialogShowCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
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
            btnbook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    showDialog();
//                    showAlertDialog();
                    Intent intent=new Intent(PayActivity.this,SuccessActivity.class);
                    startActivity(intent);

                }
            });
        }
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,  LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

        payAdapter = new PayAdapter(list,getApplicationContext());
        recyclerView.setAdapter(payAdapter);

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
        btnbook=findViewById(R.id.buttonbook);
        btnyes=findViewById(R.id.button_yes);
        btnno=findViewById(R.id.button_no);

    }
//    public void showDialog() {
//        dialog = new Dialog(PayActivity.this);
////        dialog.setTitle("Thangcode.com");
//        dialog.setContentView(R.layout.dialog);
//        dialog.show();
//        btnyes.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent=new Intent(PayActivity.this, BlankFragment.class);
//                startActivity(intent);
//
//            }
//        });
//    }
public void showAlertDialog(){
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
//    builder.setTitle("ThangCoder.Com");
    builder.setMessage("Xác nhận đặt hàng");
    builder.setCancelable(false);
    builder.setPositiveButton("HỦY", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            Toast.makeText(PayActivity.this, "Không thoát được", Toast.LENGTH_SHORT).show();

        }
    });
    builder.setNegativeButton("ĐỒNG Ý", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
//            dialogInterface.dismiss();
            Intent intent=new Intent(PayActivity.this, BlankFragment.class);
                startActivity(intent);
        }
    });
    AlertDialog alertDialog = builder.create();
    alertDialog.show();

}
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
