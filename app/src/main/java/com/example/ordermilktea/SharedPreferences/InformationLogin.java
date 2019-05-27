package com.example.ordermilktea.SharedPreferences;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class InformationLogin {
    private Activity activity;

    private FirebaseDatabase database;
    private DatabaseReference refListUser;
    private String phone;
    private FirebaseUser userFireBase;
    final private String LIST_USER = "list_user";
    final private String PHONE = "phone_number";

    public InformationLogin(Activity activity) {
        this.activity = activity;
    }

    public void setSharedPre(boolean isLogined, String uid, String phone) {
        SharedPreferences sharedPreferences = this.activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("logined", isLogined);
        editor.putString("uid", uid);
        editor.putString(PHONE, phone);
        editor.apply();
        editor.commit();
    }

    public boolean getIsLogined() {
        SharedPreferences sharedPreferences = this.activity.getPreferences(Context.MODE_PRIVATE);
        boolean is = sharedPreferences.getBoolean("logined", false);
        return is;
    }

    public String getUid(){
        SharedPreferences sharedPreferences = this.activity.getPreferences(Context.MODE_PRIVATE);
        String uid = sharedPreferences.getString("uid", "");
        return uid;
    }

    public String getPhone(){
        SharedPreferences sharedPreferences = this.activity.getPreferences(Context.MODE_PRIVATE);
        String phone = sharedPreferences.getString(PHONE, "");
        return phone;
    }

    public String getPhoneNumberFromFireBase(){
        database = FirebaseDatabase.getInstance();
        refListUser = database.getReference(LIST_USER);

        userFireBase = FirebaseAuth.getInstance().getCurrentUser();
        refListUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot user : dataSnapshot.getChildren()) {
                    String uid = user.getKey();
                    if (uid.equals(userFireBase.getUid())) {
                        phone = user.child(PHONE).getValue(String.class);
                        onReceived.onReceivecPhone(phone);
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return phone;
    }

    public void setPhone(String phone){
        database = FirebaseDatabase.getInstance();
        refListUser = database.getReference(LIST_USER);
        userFireBase = FirebaseAuth.getInstance().getCurrentUser();
        refListUser.child(userFireBase.getUid()).child(PHONE).setValue(phone);
    }

    public interface OnReceived{
        void onReceivecPhone(String phone);
    }

    private OnReceived onReceived;

    public void setOnReceived(OnReceived onReceived) {
        this.onReceived = onReceived;
    }
}
