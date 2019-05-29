package com.example.ordermilktea.Dialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ordermilktea.Activity.PayActivity;
import com.example.ordermilktea.Adapter.MilkTeaInCartAdapter;
import com.example.ordermilktea.Model.Cart;
import com.example.ordermilktea.Model.MilkTea;
import com.example.ordermilktea.Model.MilkTeaInCart;
import com.example.ordermilktea.Model.Store;
import com.example.ordermilktea.R;

import java.util.ArrayList;

public class BottomSheetDialogShowCart extends BottomSheetDialogFragment {
    private ArrayList<MilkTeaInCart> listMilkTeaInCart;
    private View viewAccept, viewRemoveCart;
    private TextView tvAccept;
    private Cart mCart;
    private MilkTeaInCartAdapter milkTeaInCartAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_sheet_cart, container, false);
        getDialog().getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        map(v);
        getBundle();
        initRecyclerView(v);
        return v;
    }

    private void map(View v) {
        tvAccept = v.findViewById(R.id.tv_accept_change_cart);
        viewAccept = v.findViewById(R.id.linearlayout_accept_change_cart);
        viewRemoveCart = v.findViewById(R.id.linearlayout_remove_cart);
        viewRemoveCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cart cart = new Cart();
                onCartChangedListener.onCartChanged(cart);
                dismiss();
            }
        });
        tvAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cart cart = getCart();
                if (cart == null) {
                    cart = new Cart();
                }
                onCartChangedListener.onCartChanged(cart);
                dismiss();
            }
        });
        viewAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cart cart = getCart();
                if (cart == null) {
                    cart = new Cart();
                }
                onCartChangedListener.onCartChanged(cart);
                dismiss();
            }
        });
    }


    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        mCart = getCart();
        if (mCart == null) {
            mCart = new Cart();
        }
        onCartChangedListener.onCancel(mCart);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void getBundle() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            Cart cart;
            cart = (Cart) bundle.getSerializable("cart");
            listMilkTeaInCart = cart.getListMilkTeaInCart();
            mCart = cart;
        }
    }

    private Cart getCart() {
        Cart cart = new Cart();
        for (int i = 0; i < listMilkTeaInCart.size(); i++) {
            MilkTeaInCart milkTeaInCart = listMilkTeaInCart.get(i);
            if (milkTeaInCart.getNumberOfOrders() == 0) {
                listMilkTeaInCart.remove(i);
                i--;
            }
        }
        cart.setListMilkTeaInCart(listMilkTeaInCart);
        cart.calSumPrice();
        return cart;
    }

    private void initRecyclerView(View v) {
        RecyclerView recyclerView = v.findViewById(R.id.recycler_view_milk_tea_in_cart);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        milkTeaInCartAdapter = new MilkTeaInCartAdapter(listMilkTeaInCart, getActivity().getApplicationContext());
        recyclerView.setAdapter(milkTeaInCartAdapter);
    }

    public interface OnCartChangedListener {
        void onCartChanged(Cart cart);

        void onCancel(Cart cart);
    }

    private OnCartChangedListener onCartChangedListener;

    public void setOnCartChangedListener(OnCartChangedListener onCartChangedListener) {
        this.onCartChangedListener = onCartChangedListener;
    }

}
