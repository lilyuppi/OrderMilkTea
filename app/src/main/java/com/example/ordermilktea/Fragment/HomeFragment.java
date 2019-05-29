package com.example.ordermilktea.Fragment;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;

import com.example.ordermilktea.Activity.MainActivity;
import com.example.ordermilktea.Activity.StoreActivity;
import com.example.ordermilktea.Adapter.AutoCompleteStoreAdapter;
import com.example.ordermilktea.Model.Store;
import com.example.ordermilktea.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private AutoCompleteTextView autoCompleteTextSearch;
    View view, viewMain;
    private ArrayList<Store> listStore;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,
                             @Nullable  Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        listStore = (ArrayList<Store>) getArguments().getSerializable("store");

        initAutocompleteTextSearch(view);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragmentAds = new AdsFragment();
        Fragment fragmentSuggest = new SuggestFragment();
        Fragment fragmentFavorite = new FavoriteFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("store", listStore);
        fragmentAds.setArguments(bundle);
        fragmentSuggest.setArguments(bundle);
        fragmentFavorite.setArguments(bundle);
        fragmentTransaction.add(R.id.fragment_ads, fragmentAds);
        fragmentTransaction.add(R.id.fragment_suggest, fragmentSuggest);
        fragmentTransaction.add(R.id.fragment_favorite, fragmentFavorite);
        fragmentTransaction.commit();
        return view;
    }


    private void initAutocompleteTextSearch(View view) {
        autoCompleteTextSearch = view.findViewById(R.id.autocomplete_text_search);
        AutoCompleteStoreAdapter autoCompleteStoreAdapter = new AutoCompleteStoreAdapter(getActivity().getApplicationContext(), listStore);
        autoCompleteTextSearch.setAdapter(autoCompleteStoreAdapter);
        autoCompleteTextSearch.setThreshold(1);
        autoCompleteTextSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        autoCompleteStoreAdapter.setOnItemClickedListener(new AutoCompleteStoreAdapter.OnItemClickedListener() {
            @Override
            public void onItemClick(Store store) {
                autoCompleteTextSearch.setText("");
                Intent intent = new Intent(getActivity().getApplicationContext(), StoreActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("store", store);
                intent.putExtras(bundle);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.anim_slide_in_from_right, R.anim.anim_slide_out_to_left);
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(getActivity().getApplicationContext().INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(autoCompleteTextSearch.getWindowToken(), 0);
                autoCompleteTextSearch.clearFocus();
            }
        });
        viewMain = view.findViewById(R.id.touch_outside_edt);
        viewMain.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (autoCompleteTextSearch.isFocused()) {
                        Rect outRect = new Rect();
                        autoCompleteTextSearch.getGlobalVisibleRect(outRect);
                        if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(getActivity().getApplicationContext().INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(autoCompleteTextSearch.getWindowToken(), 0);
                            autoCompleteTextSearch.clearFocus();
                        }
                    }
                }
                return false;
            }
        });
    }


}
