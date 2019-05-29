package com.example.ordermilktea.Firebase;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.ordermilktea.Model.Cart;
import com.example.ordermilktea.Model.HistoryModel;
import com.example.ordermilktea.Model.Information;
import com.example.ordermilktea.Model.MilkTea;
import com.example.ordermilktea.Model.MilkTeaInCart;
import com.example.ordermilktea.Model.Store;
import com.example.ordermilktea.Model.User;
import com.example.ordermilktea.SharedPreferences.InformationLogin;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
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
    final private String HISTORY = "history";
    final private String UID = "uid";
    final private String USER = "user";
    final private String NAME = "name";
    final private String CART = "cart";
    final private String DESCRIBE = "describe";
    final private String INFO = "information";
    final private String IMG = "img_src";
    final private String PRICE = "price";
    final private String ADDRESS = "address";
    final private String PHONE = "phone_number";
    final private String FREESHIP = "freeship";
    final private String AIRPAY = "airpay";
    final private String TIMEORDER = "timeorder";
    final private String DISCOUNT = "discount";
    final private String TOPPING = "topping";
    final private String NUMBER_OF_ORDERS = "number_of_orders";

    public DataFireBase(DataStoreCallBack dataStoreCallBack, Activity activity) {
        init();
        getInformationLogin(activity);
        mDataStoreCallBack = dataStoreCallBack;
        refListStore.addListenerForSingleValueEvent(new ValueEventListener() {
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

    public void getHistory() {
        if (informationLogin.getIsLogined()) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            String uid = user.getUid();
            String pathHistory = "/" + LIST_USER + "/" + uid + "/" + HISTORY;
            DatabaseReference refHistory = database.getReference(pathHistory);
            refHistory.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    ArrayList<HistoryModel> historyModelArrayList = new ArrayList<>();
                    for (DataSnapshot historyFireBase : dataSnapshot.getChildren()) {
                        HistoryModel historyModel = new HistoryModel();
                        Log.d("historyid", historyFireBase.getKey());
                        // get cart
                        Cart cart = new Cart();
                        int priceCart = historyFireBase.child(CART).child(PRICE).getValue(Integer.class);
                        ArrayList<MilkTeaInCart> milkTeaInCartArrayList = new ArrayList<>();
                        for (DataSnapshot milkTeaInCartFireBase : historyFireBase.child(CART).getChildren()) {
                            if (milkTeaInCartFireBase.getKey().equals(PRICE)) {
                                continue;
                            }
                            MilkTeaInCart milkTeaInCart = new MilkTeaInCart();
                            String nameMilk = milkTeaInCartFireBase.child(NAME).getValue(String.class);
                            Log.d("hissss", priceCart + milkTeaInCartFireBase.getKey());
                            int numOfOrders = milkTeaInCartFireBase.child(NUMBER_OF_ORDERS).getValue(Integer.class);
                            int priceMilk = milkTeaInCartFireBase.child(PRICE).getValue(Integer.class);
                            String toppingMilk = milkTeaInCartFireBase.child(TOPPING).getValue(String.class);
                            milkTeaInCart.setName(nameMilk);
//                        milkTeaInCart.setNumberOfOrders(numOfOrders);
                            milkTeaInCart.setPrice(priceMilk);
                            milkTeaInCart.setListTopping(toppingMilk);
                            milkTeaInCart.setNumberOfOrders(numOfOrders);
                            milkTeaInCartArrayList.add(milkTeaInCart);
                        }
                        cart.setSumPrice(priceCart);
                        cart.setListMilkTeaInCart(milkTeaInCartArrayList);
                        historyModel.setCart(cart);
                        // get information store
                        String addStore = historyFireBase.child(INFO).child(ADDRESS).getValue(String.class);
                        int phoneStore = historyFireBase.child(INFO).child(PHONE).getValue(Integer.class);
                        Information informationStore = new Information(addStore, phoneStore);
                        historyModel.setInformationStore(informationStore);
                        // get information user
                        User userFireBase = new User();
                        String nameUser = (String) historyFireBase.child(USER).child(NAME).getValue();
                        String addUser = (String) historyFireBase.child(USER).child(INFO).child(ADDRESS).getValue();
                        String stringPhone = historyFireBase.child(USER).child(INFO).child(PHONE).getValue(String.class);
                        int phoneUser;
                        if (stringPhone.equals("")) {
                            phoneUser = 0;
                        } else {
                            phoneUser = Integer.parseInt(stringPhone);
                        }
                        userFireBase.setName(nameUser);
                        userFireBase.setInformation(new Information(addUser, phoneUser));
                        historyModel.setUser(userFireBase);
                        // get time order
                        String timeOrder = (String) historyFireBase.child(TIMEORDER).getValue();
                        historyModel.setTimeOrder(timeOrder);
                        // get img store
                        String imgStore = (String) historyFireBase.child(IMG).getValue();
                        historyModel.setImgStore(imgStore);
                        // get name store
                        String nameStore = (String) historyFireBase.child(NAME).getValue();
                        historyModel.setNameStore(nameStore);
                        historyModelArrayList.add(historyModel);

                    }
                    ArrayList<HistoryModel> list = new ArrayList<>();
                    for (int i = historyModelArrayList.size() - 1; i >= 0; i--) {
                        list.add(historyModelArrayList.get(i));
                    }
                    mDataStoreCallBack.onReceiveHistory(list);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }


    }

    private void init() {
        database = FirebaseDatabase.getInstance();
        refListStore = database.getReference(LIST_STORE);
        refListUser = database.getReference(LIST_USER);
        listStore = new ArrayList<>();
//        randomListStore(20);
    }

    private void getInformationLogin(Activity activity) {
        informationLogin = new InformationLogin(activity);
        if (informationLogin.getIsLogined()) {
            uidLocal = informationLogin.getUid();
            refListUser.addListenerForSingleValueEvent(new ValueEventListener() {
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

    public void setNewUser(String uid) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String name = user.getDisplayName();
        refListUser.child(uid).child(NAME).setValue(name);
    }

    public void setNewCart(Information information, Cart cart, String imgStore, String nameStore) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        Date currentTime = Calendar.getInstance().getTime();
        String idCart = currentTime.getYear() + "" + currentTime.getMonth() + "" + currentTime.getDay() + "" + currentTime.getHours() + "" + currentTime.getMinutes()
                + "" + currentTime.getSeconds() + "" + uid;
        Log.d("idCart", idCart);

        String pathHistory = "/" + LIST_USER + "/" + uid + "/" + HISTORY + "/" + idCart;
        DatabaseReference refHistory = database.getReference(pathHistory);
        Log.d("uid", uid + "");
        // set time
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss - MM/dd/yyyy", Locale.ROOT);
        String timeOrder = dateFormat.format(calendar.getTime());
        refHistory.child(TIMEORDER).setValue(timeOrder);
        // set information user
        refHistory.child(USER).child(NAME).setValue(user.getDisplayName());
        refHistory.child(USER).child(INFO).child(ADDRESS).setValue("dia chi");
        refHistory.child(USER).child(INFO).child(PHONE).setValue(informationLogin.getPhone());
        // set information store
        refHistory.child(IMG).setValue(imgStore);
        refHistory.child(NAME).setValue(nameStore);
        refHistory.child(INFO).child(ADDRESS).setValue(information.getAddress());
        refHistory.child(INFO).child(PHONE).setValue(information.getPhoneNumber());
        // set cart
        refHistory.child(CART).child(PRICE).setValue(cart.getSumPrice());
        for (MilkTeaInCart milkTeaInCart : cart.getListMilkTeaInCart()) {
            String nameMilkTea = milkTeaInCart.getName();
            refHistory.child(CART).child(nameMilkTea).child(NAME).setValue(milkTeaInCart.getName());
            String listTopping = "";
            for (String topping : milkTeaInCart.getTopping()) {
                listTopping += ", ";
                listTopping += topping;
            }
            listTopping = "[" + milkTeaInCart.getIce() + ", " + milkTeaInCart.getSugar() + listTopping + "]";
            refHistory.child(CART).child(nameMilkTea).child(TOPPING).setValue(listTopping);
            refHistory.child(CART).child(nameMilkTea).child(PRICE).setValue(milkTeaInCart.getPrice());
            refHistory.child(CART).child(nameMilkTea).child(NUMBER_OF_ORDERS).setValue(milkTeaInCart.getNumberOfOrders());
        }
    }

    private void randomListMilkTea(String nameStore, int number) {
        String[] listName = {"Ô long dâu rừng", "Ô Long Đào Kem", "Lục Trà Đào", "Lục Trà Nhài", "Hồng Trà", "Trà Sữa Ổi", "Trà Xanh Kiwi", "Trà Đen Ô Mai", "Sữa Tươi Trân Châu Đường Đen",
                "Trà Bóng Mưa", "Hồng Trà Sủi Bọt", "Trà Xanh Sủi Bọt", "Trà Alisan Sủi Bọt", "Trà Sữa Thiết Quan Âm", "Trà Ô Long Đào",
                "Trà Quan Âm", "Cold Brew", "Caramel Macchiato", "Caffè Latte", "Caffè Americano", "Cappuccino", "Freeze Trà Xanh", "Trà Sen Vàng",
                "Trà Thạch Vải", "Trà Sữa Khoai Lang", "Trà Xoài Macchiato", "Mattcha Trân Châu Đường Đen", "Trà Sữa Cheese", "Hồng Trà Sữa", "Trà Gạo Nhật Cheese"
        };
        String web = "https://androidtimetable.000webhostapp.com/img/";
        String[] listImgSrc = {web + "1.jpeg", web + "2.jpg", web + "3.jpg", web + "4.jpeg", web + "5.png", web + "6.jpg", web + "7.jpg", web + "8.png", web + "9.jpg", web + "10.jpg",
                web + "11.jpg", web + "12.jpg", web + "13.jpg", web + "14.jpg", web + "15.jpg", web + "16.jpg", web + "17.jpg"
        };
        int[] listPrice = {50000, 40000, 30000, 55000, 45000, 35000, 55000, 60000};
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
        String[] listName = {"Starbuck", "Royal Tea", "Ding Tea", "Trà Sữa Heeka", "Lee Tea", "Tiger Sugar", "Trà Sữa GOATEA", "Cha Go Tea",
                "Phúc Long Tea", "Juice Garden", "Bea Tea", "Trà Sữa House Of Cha", "Trà Sữa Tocotoco", "Trà Sữa Kenta Tea", "Soya Garden"
        };
        String web = "https://androidtimetable.000webhostapp.com/img/";
        String[] listImgSrc = {web + "1.jpeg", web + "2.jpg", web + "3.jpg", web + "4.jpeg", web + "5.png", web + "6.jpg", web + "7.jpg", web + "8.png", web + "9.jpg", web + "10.jpg",
                web + "11.jpg", web + "12.jpg", web + "13.jpg", web + "14.jpg", web + "15.jpg", web + "16.jpg", web + "17.jpg"
        };
        String[] listAddress = {"Sô 1 Phạm Ngọc Thạch", "Số 2 Phạm Văn Đồng", "Số 3 Xuân Thủy", "116 Mai Dịch, Cầu Giấy", "6, Ngõ 63 Trần Quốc Vượng", "489 Hoàng Quốc Việt", "IPH Xuân Thủy", "Tầng 1 Vincom Trần Duy Hưng",
                "107 Nguyễn Hoàng", "2 Nguyễn Đổng Chi", "118 Phú Diễn", "51 Xuân La, Tây Hồ", "93 Lê Văn Hiến", "54 Vũ Trọng Phụng"
        };
        int[] listPhoneNumber = {19008198, 19002090, 18003421, 19002949, 18001848, 18099988, 19028884};
        int[] listDiscount = {10, 20, 30, 40, 45, 50, 25, 35};
        int[] listNumberOfOrders = {100, 200, 300, 230, 1000, 600, 700, 500, 150, 450};
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
            randomListMilkTea(i + "", 30);
        }
    }
}
