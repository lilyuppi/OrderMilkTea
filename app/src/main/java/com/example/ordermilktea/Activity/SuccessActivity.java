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
    private TextView tvSuccess;
    private ImageView imvSuccess1, imvSuccess2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        tvSuccess = findViewById(R.id.tvSuccess);
        imvSuccess1 = findViewById(R.id.blacksuccess);
        imvSuccess2 = findViewById(R.id.imvGreenSuccess);

        Thread timer = new Thread() { // hàm chạy gif sau 5s tự chuyển sang màn hình chính
            public void run() {
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    imvSuccess1.setVisibility(View.VISIBLE);
                    imvSuccess2.setVisibility(View.GONE);
                }
            }
        };
        timer.start();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                imvSuccess1.setVisibility(View.GONE);
                imvSuccess2.setVisibility(View.VISIBLE);
            }
        }, 3000);

        Thread transistion = new Thread() { // hàm chạy gif sau 5s tự chuyển sang màn hình chính
            public void run() {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Intent intent = new Intent(SuccessActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        };
        transistion.start();
    }

    protected void onPause() {
        super.onPause();
        finish();
    }

}
