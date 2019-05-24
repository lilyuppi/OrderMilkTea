package com.example.ordermilktea.Dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ordermilktea.Model.MilkTea;
import com.example.ordermilktea.Model.MilkTeaInCart;
import com.example.ordermilktea.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class BottomSheetDialogAddToCart extends BottomSheetDialogFragment {
    private TextView tvName, tvPrice, tvNumberOfOrders, tvPriceAfterTopping, tvNumberOfOrdersAdd;
    private View viewAddToCart;
    private RadioGroup radioGroupSugar, radioGroupIce;
    private ImageView imvMilkTea, imvAdd, imvSub;
    private String nameMilkTea;
    private int priceMilkTea, priceMilkTeaAfterTopping, numberOfOrdersAdd;
    private CheckBox cbTopping1, cbTopping2, cbTopping3, cbTopping4, cbTopping5;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_sheet_add, container, false);
        map(v);
        listener();
        getBundle();
        return v;
    }

    private void listener() {
        imvSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numberOfOrdersAdd > 0) {
                    numberOfOrdersAdd -= 1;
                    tvNumberOfOrdersAdd.setText("" + numberOfOrdersAdd);
                    updatePrice();
                }
            }
        });
        imvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberOfOrdersAdd += 1;
                tvNumberOfOrdersAdd.setText("" + numberOfOrdersAdd);
                updatePrice();
            }
        });
        viewAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MilkTeaInCart milkTeaInCart = getMilkTeaToCart();
                onToppingSelectedListener.onToppingSelected(milkTeaInCart);
                dismiss();
            }
        });
        cbTopping1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                MilkTeaInCart milkTeaInCart = getMilkTeaToCart();
                if (isChecked) {
                    priceMilkTeaAfterTopping += milkTeaInCart.getPriceTopping();
                }else {
                    priceMilkTeaAfterTopping -= milkTeaInCart.getPriceTopping();
                }
                updatePrice();
            }
        });
        cbTopping2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                MilkTeaInCart milkTeaInCart = getMilkTeaToCart();
                if (isChecked) {
                    priceMilkTeaAfterTopping += milkTeaInCart.getPriceTopping();
                }else {
                    priceMilkTeaAfterTopping -= milkTeaInCart.getPriceTopping();
                }
                updatePrice();
            }
        });
        cbTopping3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                MilkTeaInCart milkTeaInCart = getMilkTeaToCart();
                if (isChecked) {
                    priceMilkTeaAfterTopping += milkTeaInCart.getPriceTopping();
                }else {
                    priceMilkTeaAfterTopping -= milkTeaInCart.getPriceTopping();
                }
                updatePrice();
            }
        });
        cbTopping4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                MilkTeaInCart milkTeaInCart = getMilkTeaToCart();
                if (isChecked) {
                    priceMilkTeaAfterTopping += milkTeaInCart.getPriceTopping();
                }else {
                    priceMilkTeaAfterTopping -= milkTeaInCart.getPriceTopping();
                }
                updatePrice();
            }
        });
        cbTopping5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                MilkTeaInCart milkTeaInCart = getMilkTeaToCart();
                if (isChecked) {
                    priceMilkTeaAfterTopping += milkTeaInCart.getPriceTopping();
                }else {
                    priceMilkTeaAfterTopping -= milkTeaInCart.getPriceTopping();
                }
                updatePrice();
            }
        });
    }

    private void map(View view) {
        tvName = view.findViewById(R.id.tv_name_milk_tea);
        tvPrice = view.findViewById(R.id.tv_price_milk_tea);
        tvNumberOfOrders = view.findViewById(R.id.tv_number_of_orders);
        tvPriceAfterTopping = view.findViewById(R.id.tv_price_milk_tea_after_topping);
        tvNumberOfOrdersAdd = view.findViewById(R.id.tv_number_of_orders_add);
        imvMilkTea = view.findViewById(R.id.imv_milk_tea);
        imvAdd = view.findViewById(R.id.imv_add);
        imvSub = view.findViewById(R.id.imv_sub);
        imvAdd.setVisibility(View.VISIBLE);
        imvSub.setVisibility(View.VISIBLE);
        tvNumberOfOrdersAdd.setVisibility(View.VISIBLE);
        viewAddToCart = view.findViewById(R.id.linearlayout_add_to_card_button);
        radioGroupSugar = view.findViewById(R.id.select_sugar_group);
        radioGroupIce = view.findViewById(R.id.select_ice_group);
        cbTopping1 = view.findViewById(R.id.select_topping_1);
        cbTopping2 = view.findViewById(R.id.select_topping_2);
        cbTopping3 = view.findViewById(R.id.select_topping_3);
        cbTopping4 = view.findViewById(R.id.select_topping_4);
        cbTopping5 = view.findViewById(R.id.select_topping_5);
        numberOfOrdersAdd = 1;
        tvNumberOfOrdersAdd.setText("" + numberOfOrdersAdd);
    }

    private void updatePrice() {
        int sum = priceMilkTeaAfterTopping * numberOfOrdersAdd;
        String str = NumberFormat.getNumberInstance(Locale.US).format(sum);
        tvPriceAfterTopping.setText(str);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    private MilkTeaInCart getMilkTeaToCart() {
        MilkTeaInCart milkTeaInCart = new MilkTeaInCart();
        String sugar = getSugar();
        String ice = getIce();
        ArrayList<String> listTopping = getTopping();
        milkTeaInCart.setName(nameMilkTea);
        milkTeaInCart.setPrice(priceMilkTeaAfterTopping);
        milkTeaInCart.setIce(ice);
        milkTeaInCart.setSugar(sugar);
        milkTeaInCart.setTopping(listTopping);
        milkTeaInCart.setNumberOfOrders(numberOfOrdersAdd);
        return milkTeaInCart;
    }


    private String getSugar() {
        int id = radioGroupSugar.getCheckedRadioButtonId();
        switch (id) {
            case R.id.select_sugar_0:
                return getString(R.string.sugar_0);
            case R.id.select_sugar_25:
                return getString(R.string.sugar_25);
            case R.id.select_sugar_50:
                return getString(R.string.sugar_50);
            case R.id.select_sugar_75:
                return getString(R.string.sugar_75);
            case R.id.select_sugar_100:
                return getString(R.string.sugar_100);
        }
        return getString(R.string.sugar_100);
    }

    private String getIce() {
        int id = radioGroupIce.getCheckedRadioButtonId();
        switch (id) {
            case R.id.select_ice_0:
                return getString(R.string.ice_0);
            case R.id.select_ice_25:
                return getString(R.string.ice_25);
            case R.id.select_ice_50:
                return getString(R.string.ice_50);
            case R.id.select_ice_75:
                return getString(R.string.ice_75);
            case R.id.select_ice_100:
                return getString(R.string.ice_100);
        }
        return getString(R.string.ice_100);
    }

    private ArrayList<String> getTopping() {
        ArrayList<String> listTopping = new ArrayList<>();
        if (cbTopping1.isChecked()) {
            listTopping.add(getString(R.string.topping_1));
        }
        if (cbTopping2.isChecked()) {
            listTopping.add(getString(R.string.topping_2));
        }
        if (cbTopping3.isChecked()) {
            listTopping.add(getString(R.string.topping_3));
        }
        if (cbTopping4.isChecked()) {
            listTopping.add(getString(R.string.topping_4));
        }
        if (cbTopping5.isChecked()) {
            listTopping.add(getString(R.string.topping_5));
        }
        return listTopping;
    }

    private void getBundle() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            MilkTea milkTea = (MilkTea) bundle.getSerializable("milktea");
            tvName.setText(milkTea.getName());
            nameMilkTea = milkTea.getName();
            String str = NumberFormat.getNumberInstance(Locale.US).format(milkTea.getPrice());
            tvPrice.setText(str + "đ");
            priceMilkTea = milkTea.getPrice();
            priceMilkTeaAfterTopping = priceMilkTea;
            str = NumberFormat.getNumberInstance(Locale.US).format(milkTea.getPrice());
            tvPriceAfterTopping.setText(str + "đ");
            tvNumberOfOrders.setText(milkTea.getNumberOfOrders() + "+");
            Glide.with(this).load(milkTea.getImgSrc()).into(imvMilkTea);
        }
    }


    public interface OnToppingSelectedListener {
        void onToppingSelected(MilkTeaInCart milkTeaInCart);
    }

    private OnToppingSelectedListener onToppingSelectedListener;

    public void setOnReceivedToppingListener(OnToppingSelectedListener onToppingSelectedListener) {
        this.onToppingSelectedListener = onToppingSelectedListener;
    }
}
