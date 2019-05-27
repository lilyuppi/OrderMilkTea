package com.example.ordermilktea.SharedPreferences;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class InformationLogin {
    private Activity activity;

    public InformationLogin(Activity activity) {
        this.activity = activity;
    }

    public void setSharedPre(boolean isLogined, String uid) {
        SharedPreferences sharedPreferences = this.activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("logined", isLogined);
        editor.putString("uid", uid);
        editor.apply();
        editor.commit();
    }

    public void setPhone(int phone) {
        SharedPreferences sharedPreferences = this.activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("phone", phone);
        editor.apply();
        editor.commit();
    }

    public int getPhone(){

        SharedPreferences sharedPreferences = this.activity.getPreferences(Context.MODE_PRIVATE);
        int phone = sharedPreferences.getInt("phone", 0);
        return phone;
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
}
