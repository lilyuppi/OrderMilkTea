package com.example.ordermilktea.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ordermilktea.Model.MilkTeaInCart;
import com.example.ordermilktea.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    ArrayList<MilkTeaInCart> milkTeaInCartArrayList;
    Context context;

    public HistoryAdapter(ArrayList<MilkTeaInCart> milkTeaInCartArrayList, Context context) {
        this.milkTeaInCartArrayList = milkTeaInCartArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater=LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.dong_history,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return milkTeaInCartArrayList.size();    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgstore;
        TextView namestore,addressstore,address,timeorder,numberorder,money,methodpay;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgstore=itemView.findViewById(R.id.imagestore);
            namestore=itemView.findViewById(R.id.namestore);
            addressstore=itemView.findViewById(R.id.nameaddress);
            address=itemView.findViewById(R.id.youraddress);
            timeorder=itemView.findViewById(R.id.timeordered);
            numberorder=itemView.findViewById(R.id.numberordered);
            money=itemView.findViewById(R.id.moneyordered);
            methodpay=itemView.findViewById(R.id.methodpay);

        }
    }
}
