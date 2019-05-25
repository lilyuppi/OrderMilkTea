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
import com.example.ordermilktea.Model.MilkTea;
import com.example.ordermilktea.Model.Store;
import com.example.ordermilktea.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class MilkTeaAdapter extends RecyclerView.Adapter<MilkTeaAdapter.ViewHolder> {
    ArrayList<MilkTea> listMilkTea;
    Context context;

    public MilkTeaAdapter(ArrayList<MilkTea> listMilkTea, Context context) {
        this.listMilkTea = listMilkTea;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_milk_tea, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final MilkTea milkTea = listMilkTea.get(i);
        viewHolder.tvName.setText(milkTea.getName());
        viewHolder.tvDescribe.setText(milkTea.getDescribe());
        String str = NumberFormat.getNumberInstance(Locale.US).format(milkTea.getPrice());
        viewHolder.tvPrice.setText( str + "đ");
        viewHolder.tvNumberOfOrders.setText(milkTea.getNumberOfOrders() + "+");
        Glide.with(context).load(milkTea.getImgSrc()).into(viewHolder.imvMilkTea);

        viewHolder.imvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickedListener.onItemClick(milkTea);
            }
        });
        viewHolder.viewItemMilkTea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickedListener.onItemClick(milkTea);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listMilkTea.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvName, tvDescribe, tvPrice, tvNumberOfOrders;
        View viewItemMilkTea;
        ImageView imvMilkTea, imvAdd;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name_milk_tea);
            tvDescribe = itemView.findViewById(R.id.tv_describe_milk_tea);
            tvPrice = itemView.findViewById(R.id.tv_price_milk_tea);
            tvNumberOfOrders = itemView.findViewById(R.id.tv_number_of_orders);
            imvMilkTea = itemView.findViewById(R.id.imv_milk_tea);
            imvAdd = itemView.findViewById(R.id.imv_add);
            viewItemMilkTea = itemView.findViewById(R.id.linearlayout_item_milk_tea);
        }
    }

    public interface OnItemClickedListener{
        void onItemClick(MilkTea milkTea);
    }

    private OnItemClickedListener onItemClickedListener;

    public void setOnItemClickedListener(OnItemClickedListener onItemClickedListener) {
        this.onItemClickedListener = onItemClickedListener;
    }
}
