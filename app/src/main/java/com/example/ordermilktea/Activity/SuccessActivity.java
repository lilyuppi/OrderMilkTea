package com.example.ordermilktea.Activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ordermilktea.R;

public class SuccessActivity extends AppCompatActivity {
    TextView success;
    ImageView imgsuccess0, imgsucces1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        success = findViewById(R.id.successtext);
        imgsuccess0 = findViewById(R.id.blacksuccess);
        imgsucces1 = findViewById(R.id.greensuccess);

        Thread bamgio = new Thread() { // hàm chạy gif sau 5s tự chuyển sang màn hình chính
            public void run() {
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    imgsuccess0.setVisibility(View.VISIBLE);
                    imgsucces1.setVisibility(View.GONE);
                }
            }
        };
        bamgio.start();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                imgsuccess0.setVisibility(View.GONE);
                imgsucces1.setVisibility(View.VISIBLE);
            }
        }, 3000);

        Thread chuyen = new Thread() { // hàm chạy gif sau 5s tự chuyển sang màn hình chính
            public void run() {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Intent intent = new Intent(SuccessActivity.this,MainActivity.class);
                    startActivity(intent);
                }
            }
        };
        chuyen.start();
    }

    protected void onPause(){
        super.onPause();
        finish();
    }

}
