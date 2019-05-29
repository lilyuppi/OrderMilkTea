package com.example.ordermilktea.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ordermilktea.Model.MilkTea;
import com.example.ordermilktea.Model.MilkTeaInCart;
import com.example.ordermilktea.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class MilkTeaInCartAdapter extends RecyclerView.Adapter<MilkTeaInCartAdapter.ViewHolder> {
    private ArrayList<MilkTeaInCart> listMilkTeaInCart;
    private Context context;

    public MilkTeaInCartAdapter(ArrayList<MilkTeaInCart> listMilkTeaInCart, Context context) {
        this.listMilkTeaInCart = listMilkTeaInCart;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_milk_tea_in_cart, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        final MilkTeaInCart milkTeaInCart = listMilkTeaInCart.get(i);
        final int numberOfOrders = milkTeaInCart.getNumberOfOrders();
        viewHolder.tvName.setText(milkTeaInCart.getName());
        viewHolder.tvSumItem.setText(numberOfOrders + "");
        String listTopping = "";
        for (String topping : milkTeaInCart.getTopping()) {
            listTopping += ", ";
            listTopping += topping;
        }
        viewHolder.tvTopping.setText("[" + milkTeaInCart.getIce() + ", " + milkTeaInCart.getSugar() + listTopping + "]");
        int number_of_orders = milkTeaInCart.getNumberOfOrders();
        int price_one = milkTeaInCart.getPrice();
        int price_sum = number_of_orders * price_one;
        String str = NumberFormat.getNumberInstance(Locale.US).format(price_sum);
        viewHolder.tvPrice.setText(price_one + "*" + number_of_orders + "=" + str);
        viewHolder.imvSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numberOfOrders > 0) {
                    milkTeaInCart.setNumberOfOrders(numberOfOrders - 1);
                    listMilkTeaInCart.set(i, milkTeaInCart);
                    notifyItemChanged(i);
                }
            }
        });
        viewHolder.imvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                milkTeaInCart.setNumberOfOrders(numberOfOrders + 1);
                listMilkTeaInCart.set(i, milkTeaInCart);
                notifyItemChanged(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listMilkTeaInCart.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvTopping, tvPrice, tvSumItem;
        ImageView imvSub, imvAdd;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name_milk_tea_in_cart);
            tvTopping = itemView.findViewById(R.id.tv_topping_milk_tea_in_cart);
            tvPrice = itemView.findViewById(R.id.tv_price_sum_of_item_milk_tea_in_cart);
            tvSumItem = itemView.findViewById(R.id.tv_number_of_orders_in_cart);
            imvAdd = itemView.findViewById(R.id.imv_add_in_cart);
            imvSub = itemView.findViewById(R.id.imv_sub_in_cart);
        }
    }

}
