package com.example.assignment_3;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {
private static final int  PAUSE=3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {

            public void run() {
                Intent intent = new Intent(SplashActivity.this, StudentListActivity.class);
                startActivity(intent);
               finish();

            }
        }, PAUSE);

    }
    }

