package com.example.ordermilktea.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ordermilktea.Model.Store;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.example.ordermilktea.R;

import java.util.ArrayList;

public class SuggestAdapter  extends RecyclerView.Adapter<SuggestAdapter.ViewHolder> {

    Context context;
    ArrayList<Store> listStore;

    public SuggestAdapter(Context context, ArrayList<Store> stores) {
        this.context = context;
        this.listStore = stores;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.dong_suggest,viewGroup,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        final Store store = listStore.get(i);
        int discount = store.getDiscount();
        int set= store.getNumberOfOrders();
        viewHolder.tenquan.setText(store.getName());
        Glide.with(context).load(store.getImgSrc()).into(viewHolder.anhquan);
        viewHolder.soluong.setText(String.valueOf(set)+"+");
        viewHolder.sale.setText(String.valueOf(discount)+ "%");
        viewHolder.relativeLayout_suggest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickedListener.onItemClick(store);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listStore.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView anhquan;
        TextView tenquan;
        TextView soluong;
        TextView sale;
        RelativeLayout relativeLayout_suggest;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            anhquan=itemView.findViewById(R.id.imageviewsuggest);
            tenquan=itemView.findViewById(R.id.textviewsuggest);
            soluong=itemView.findViewById(R.id.soluongdat);
            sale = itemView.findViewById(R.id.sale);
            relativeLayout_suggest = itemView.findViewById(R.id.relative_layout_suggest);

        }
    }
    public interface OnItemClickedListener{
        void onItemClick(Store store);
    }
    private OnItemClickedListener onItemClickedListener;

    public void setOnItemClickedListener(OnItemClickedListener onItemClickedListener) {
        this.onItemClickedListener = onItemClickedListener;
    }
}
