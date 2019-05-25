package com.example.ordermilktea.Firebase;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.example.ordermilktea.Model.Information;
import com.example.ordermilktea.Model.MilkTea;
import com.example.ordermilktea.Model.Store;
import com.example.ordermilktea.SharedPreferences.InformationLogin;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

public class DataFireBase {
    private FirebaseDatabase database;
    private DatabaseReference refListStore;
    private DatabaseReference refListUser;
    private DataStoreCallBack mDataStoreCallBack;
    private ArrayList<Store> listStore;
    private String uidLocal;
    private InformationLogin informationLogin;
    final private String LIST_STORE = "list_store";
    final private String LIST_MILK_TEA = "list_milk_tea";
    final private String LIST_USER = "list_user";
    final private String UID = "uid";
    final private String NAME = "name";
    final private String DESCRIBE = "describe";
    final private String INFO = "information";
    final private String IMG = "img_src";
    final private String PRICE = "price";
    final private String ADDRESS = "address";
    final private String PHONE = "phone_number";
    final private String FREESHIP = "freeship";
    final private String AIRPAY = "airpay";
    final private String DISCOUNT = "discount";
    final private String NUMBER_OF_ORDERS = "number_of_orders";
    public DataFireBase(DataStoreCallBack dataStoreCallBack) {
        init();
        getInformationLogin(dataStoreCallBack);
        mDataStoreCallBack = dataStoreCallBack;
        refListStore.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot storeFireBase : dataSnapshot.getChildren()) {
                    // get name
                    String nameStore = storeFireBase.child(NAME).getValue(String.class);
                    // get image resource
                    String imgSrcStore = storeFireBase.child(IMG).getValue(String.class);
                    // get information
                    String address = storeFireBase.child(INFO).child(ADDRESS).getValue(String.class);
                    int phoneNumber = storeFireBase.child(INFO).child(PHONE).getValue(Integer.class);
                    Information information = new Information(address, phoneNumber);
                    // get isFreeShip
                    boolean isFreeShip = storeFireBase.child(FREESHIP).getValue(boolean.class);
                    // get isAirpay
                    boolean isAirPay = storeFireBase.child(AIRPAY).getValue(boolean.class);
                    // get discount
                    int discount = storeFireBase.child(DISCOUNT).getValue(Integer.class);
                    // get number of orders
                    int numberOfOrders = storeFireBase.child(NUMBER_OF_ORDERS).getValue(Integer.class);
                    // get list milk tea
                    ArrayList<MilkTea> listMilkTea = new ArrayList<>();
                    for (DataSnapshot milkTeaFireBase : storeFireBase.child(LIST_MILK_TEA).getChildren()) {
                        // get name
                        String nameMilkTea = milkTeaFireBase.child(NAME).getValue(String.class);
                        // get describe
                        String describeMilkTea = milkTeaFireBase.child(DESCRIBE).getValue(String.class);
                        // get price
                        int priceMilkTea = milkTeaFireBase.child(PRICE).getValue(Integer.class);
                        // get image resource
                        String imgSrcMilkTea = milkTeaFireBase.child(IMG).getValue(String.class);
                        // get number of orders
                        int numberOfOrderMilktea = milkTeaFireBase.child(NUMBER_OF_ORDERS).getValue(Integer.class);

                        // init milk tea
                        MilkTea milkTea = new MilkTea();
                        milkTea.setName(nameMilkTea);
                        milkTea.setDescribe(describeMilkTea);
                        milkTea.setImgSrc(imgSrcMilkTea);
                        milkTea.setPrice(priceMilkTea);
                        milkTea.setNumberOfOrders(numberOfOrderMilktea);
                        listMilkTea.add(milkTea);
                    }

                    // init store
                    Store store = new Store();
                    store.setName(nameStore);
                    store.setImgSrc(imgSrcStore);
                    store.setInformation(information);
                    store.setFreeShip(isFreeShip);
                    store.setAirPay(isAirPay);
                    store.setDiscount(discount);
                    store.setNumberOfOrders(numberOfOrders);
                    store.setListMilkTea(listMilkTea);
                    // add store to list
                    listStore.add(store);
                }
                mDataStoreCallBack.onReceiveListStore(listStore);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void init() {
        database = FirebaseDatabase.getInstance();
        refListStore = database.getReference(LIST_STORE);
        refListUser = database.getReference(LIST_USER);
        listStore = new ArrayList<>();
//        randomListStore(20);
    }

    private void getInformationLogin(DataStoreCallBack dataStoreCallBack) {
        informationLogin = new InformationLogin((Activity) dataStoreCallBack);
        if (informationLogin.getIsLogined()) {
            uidLocal = informationLogin.getUid();
            refListUser.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    boolean isExisted = false;
                    for (DataSnapshot user : dataSnapshot.getChildren()) {
                        String uid = user.getKey();
                        if (uid.equals(uidLocal)) {
                            isExisted = true;
                        }
                    }
                    if (!isExisted) {
                        setNewUser(uidLocal);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    private void setNewUser(String uid) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String name = user.getDisplayName();
        refListUser.child(uid).child(NAME).setValue(name);
    }

    private void randomListMilkTea(String nameStore, int number) {
        String[] listName = {"Ô long dâu rừng", "Ô Long Đào Kem", "Lục Trà Đào", "Lục Trà Nhài", "Hồng Trà"};
        String web = "https://androidtimetable.000webhostapp.com/img/";
        String[] listImgSrc = {web + "1.jpeg", web + "2.jpg", web + "3.jpg", web + "4.jpeg", web + "5.png", web + "6.jpg", web + "7.jpg", web + "8.png", web + "9.jpg", web + "10.jpg"};
        int[] listPrice = {50000, 40000, 30000};
        String[] listDescribe = {"mo ta", "cai gi mo ta", "mot hai ha"};
        int[] listNumberOfOrders = {100, 200, 300, 230};
        for (int i = 0; i < number; i++) {
            int randomName = new Random().nextInt(listName.length);
            refListStore.child(nameStore).child(LIST_MILK_TEA).child(i + "").child(NAME).setValue(listName[randomName]);
            int randomImg = new Random().nextInt(listImgSrc.length);
            refListStore.child(nameStore).child(LIST_MILK_TEA).child(i + "").child(IMG).setValue(listImgSrc[randomImg]);
            int randomPrice = new Random().nextInt(listPrice.length);
            refListStore.child(nameStore).child(LIST_MILK_TEA).child(i + "").child(PRICE).setValue(listPrice[randomPrice]);
            int randomDescribe = new Random().nextInt(listDescribe.length);
            refListStore.child(nameStore).child(LIST_MILK_TEA).child(i + "").child(DESCRIBE).setValue(listDescribe[randomDescribe]);
            int randomNumberOfOrders = new Random().nextInt(listNumberOfOrders.length);
            refListStore.child(nameStore).child(LIST_MILK_TEA).child(i + "").child(NUMBER_OF_ORDERS).setValue(listNumberOfOrders[randomNumberOfOrders]);
        }
    }

    private void randomListStore(int number) {
        String[] listName = {"Starbuck", "Royal Tea", "Ding Tea", "Heeka", "Lee Tea"};
        String web = "https://androidtimetable.000webhostapp.com/img/";
        String[] listImgSrc = {web + "1.jpeg", web + "2.jpg", web + "3.jpg", web + "4.jpeg", web + "5.png", web + "6.jpg", web + "7.jpg", web + "8.png", web + "9.jpg", web + "10.jpg"};
        String[] listAddress = {"Sô 1 Phạm Ngọc Thạch", "Số 2 Phạm Văn Đồng", "Số 3 Xuân Thủy"};
        int[] listPhoneNumber = {19008198, 19002090, 18003421};
        int[] listDiscount = {10, 20, 30, 40};
        int[] listNumberOfOrders = {100, 200, 300, 230};
        for (int i = 0; i < number; i++) {
            int randomName = new Random().nextInt(listName.length);
            refListStore.child(i + "").child(NAME).setValue(listName[randomName]);
            int randomImgSrc = new Random().nextInt(listImgSrc.length);
            refListStore.child(i + "").child(IMG).setValue(listImgSrc[randomImgSrc]);
            int randomAddress = new Random().nextInt(listAddress.length);
            refListStore.child(i + "").child(INFO).child(ADDRESS).setValue(listAddress[randomAddress]);
            int randomPhone = new Random().nextInt(listPhoneNumber.length);
            refListStore.child(i + "").child(INFO).child(PHONE).setValue(listPhoneNumber[randomPhone]);
            int randomDiscount = new Random().nextInt(listDiscount.length);
            refListStore.child(i + "").child(DISCOUNT).setValue(listDiscount[randomDiscount]);
            int randomNumberOfOrders = new Random().nextInt(listNumberOfOrders.length);
            refListStore.child(i + "").child(NUMBER_OF_ORDERS).setValue(listNumberOfOrders[randomNumberOfOrders]);
            refListStore.child(i + "").child(FREESHIP).setValue(true);
            refListStore.child(i + "").child(AIRPAY).setValue(true);
            randomListMilkTea(i + "",20);
        }
    }
}
