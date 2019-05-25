package com.example.ordermilktea.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ordermilktea.Model.MilkTeaInCart;
import com.example.ordermilktea.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class PayAdapter extends RecyclerView.Adapter<PayAdapter.ViewHolder> {

    ArrayList<MilkTeaInCart> milkTeaInCartArrayList;
    Context context;

    public PayAdapter(ArrayList<MilkTeaInCart> milkTeaInCartArrayList, Context context) {
        this.milkTeaInCartArrayList = milkTeaInCartArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater=LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.dong_pay,viewGroup,false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final MilkTeaInCart milkTeaInCart = milkTeaInCartArrayList.get(i);
        final int numberOfOrders = milkTeaInCart.getNumberOfOrders();
        viewHolder.soluongsanpham.setText(numberOfOrders+"");
        viewHolder.tensanpham.setText(milkTeaInCart.getName());
        viewHolder.tien.setText(milkTeaInCart.getPrice()+"");
        String listTopping = "";
        for (String topping : milkTeaInCart.getTopping()) {
            listTopping += ", ";
            listTopping += topping;
        }
        viewHolder.topping.setText("[" + milkTeaInCart.getIce() + ", " + milkTeaInCart.getSugar() + listTopping + "]");
        /**
         *  tổng tiền cho từng sản phẩm
         */
        int price_one = milkTeaInCart.getPrice();
        int price_sum = numberOfOrders * price_one;
        String str = NumberFormat.getNumberInstance(Locale.US).format(price_sum);
        viewHolder.tien.setText(str);

    }

    @Override
    public int getItemCount() {
        return milkTeaInCartArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView soluongsanpham,tensanpham,tien,topping,tongsoluong;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            soluongsanpham=itemView.findViewById(R.id.so_luong_san_pham);
            tensanpham=itemView.findViewById(R.id.ten_san_pham);
            tien=itemView.findViewById(R.id.money);
            topping=itemView.findViewById(R.id.sugar_ice_topping);
            tongsoluong=itemView.findViewById(R.id.tong_so_luong);
        }
    }
}
