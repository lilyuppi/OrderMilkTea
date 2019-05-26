package com.example.ordermilktea.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ordermilktea.Model.Store;
import com.example.ordermilktea.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class AutoCompleteStoreAdapter extends ArrayAdapter<Store> {
    private List<Store> listStoreFull;

    public AutoCompleteStoreAdapter(@NonNull Context context, @NonNull List<Store> listStore) {
        super(context, 0, listStore);
        listStoreFull = new ArrayList<>(listStore);
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return storeFilter;
    }

    @NonNull
    @Override
    public View getView(int position, @NonNull View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.dong_suggest, parent, false);
        }

        TextView tvName = convertView.findViewById(R.id.textviewsuggest);
        ImageView imvAvatar = convertView.findViewById(R.id.imageviewsuggest);
        TextView tvNumberOfOrders = convertView.findViewById(R.id.soluongdat);
        TextView tvDiscount = convertView.findViewById(R.id.sale);
        View viewAirPay = convertView.findViewById(R.id.airPay);
        View viewFreeShip = convertView.findViewById(R.id.freeShip);
        View viewItem = convertView.findViewById(R.id.relative_layout_suggest);
        final Store store = getItem(position);
        viewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickedListener.onItemClick(store);
            }
        });
        if (store != null) {
            tvName.setText(store.getName());
            Glide.with(getContext()).load(store.getImgSrc()).into(imvAvatar);
            tvNumberOfOrders.setText(store.getInformation().getAddress());
            tvDiscount.setText(store.getDiscount() + "%");
        }

        return convertView;
    }

    private Filter storeFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<Store> suggestions = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                suggestions.addAll(listStoreFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Store store : listStoreFull) {
                    if (store.getName().toLowerCase().contains(filterPattern) || store.getInformation().getAddress().toLowerCase().contains(filterPattern)) {
                        suggestions.add(store);
                    }
                }
            }
            results.values = suggestions;
            results.count = suggestions.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            addAll((List) results.values);
            notifyDataSetChanged();
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((Store) resultValue).getName();
        }
    };

    public interface OnItemClickedListener{
        void onItemClick(Store store);
    }

    private OnItemClickedListener onItemClickedListener;

    public void setOnItemClickedListener(OnItemClickedListener onItemClickedListener) {
        this.onItemClickedListener = onItemClickedListener;
    }
}
