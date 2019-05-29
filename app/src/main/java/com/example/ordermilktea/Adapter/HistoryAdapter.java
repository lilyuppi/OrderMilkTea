package com.example.ordermilktea.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ordermilktea.Model.HistoryModel;
import com.example.ordermilktea.Model.MilkTeaInCart;
import com.example.ordermilktea.R;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    private ArrayList<HistoryModel> historyModelArrayList;
    private Context context;

    public HistoryAdapter(ArrayList<HistoryModel> historyModelArrayList, Context context) {
        this.historyModelArrayList = historyModelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_history, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.ViewHolder viewHolder, int i) {
        HistoryModel historyModel = historyModelArrayList.get(i);
        Glide.with(context).load(historyModel.getImgStore()).into(viewHolder.imgstore);
        viewHolder.namestore.setText(historyModel.getNameStore());
        viewHolder.addressstore.setText(historyModel.getInformationStore().getAddress());
        viewHolder.timeorder.setText(historyModel.getTimeOrder());

        ArrayList<MilkTeaInCart> list = historyModel.getCart().getListMilkTeaInCart();
        int numberofOrders = 0;
        for (int i2 = 0; i2 < list.size(); i2++) {
            numberofOrders += list.get(i2).getNumberOfOrders();
        }
        viewHolder.numberorder.setText(numberofOrders + "");
        viewHolder.money.setText(historyModel.getCart().getSumPrice() + "");
    }

    @Override
    public int getItemCount() {
        return historyModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgstore;
        TextView namestore, addressstore, address, timeorder, numberorder, money, methodpay;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgstore = itemView.findViewById(R.id.imv_store);
            namestore = itemView.findViewById(R.id.tv_name_store);
            addressstore = itemView.findViewById(R.id.tv_add_store);
            address = itemView.findViewById(R.id.tv_add_user);
            timeorder = itemView.findViewById(R.id.tv_time_order);
            numberorder = itemView.findViewById(R.id.tv_num_of_order);
            money = itemView.findViewById(R.id.tv_sum_price);
            methodpay = itemView.findViewById(R.id.tv_method);

        }
    }
}
