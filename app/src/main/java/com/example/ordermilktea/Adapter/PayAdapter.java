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

    private ArrayList<MilkTeaInCart> milkTeaInCartArrayList;
    private Context context;

    public PayAdapter(ArrayList<MilkTeaInCart> milkTeaInCartArrayList, Context context) {
        this.milkTeaInCartArrayList = milkTeaInCartArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_pay, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final MilkTeaInCart milkTeaInCart = milkTeaInCartArrayList.get(i);
        final int numberOfOrders = milkTeaInCart.getNumberOfOrders();
        viewHolder.tvNumOfOrders.setText(numberOfOrders + "");
        viewHolder.tvName.setText(milkTeaInCart.getName());
        viewHolder.tvPrice.setText(milkTeaInCart.getPrice() + "");
        String listTopping = "";
        for (String topping : milkTeaInCart.getTopping()) {
            listTopping += ", ";
            listTopping += topping;
        }
        viewHolder.tvTopping.setText("[" + milkTeaInCart.getIce() + ", " + milkTeaInCart.getSugar() + listTopping + "]");
        /**
         *  tổng tiền cho từng sản phẩm
         */
        int price_one = milkTeaInCart.getPrice();
        int price_sum = numberOfOrders * price_one;
        String str = NumberFormat.getNumberInstance(Locale.US).format(price_sum);
        viewHolder.tvPrice.setText(str);

    }

    @Override
    public int getItemCount() {
        return milkTeaInCartArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNumOfOrders, tvName, tvPrice, tvTopping, tvAllNumOfOrders;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNumOfOrders = itemView.findViewById(R.id.tv_num_of_order);
            tvName = itemView.findViewById(R.id.tv_name);
            tvPrice = itemView.findViewById(R.id.money);
            tvTopping = itemView.findViewById(R.id.tv_sugar_ice_topping);
            tvAllNumOfOrders = itemView.findViewById(R.id.tv_all_num_of_orders);
        }
    }
}
