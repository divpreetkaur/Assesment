package com.example.assignment5.activity;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.assignment5.R;
import com.example.assignment5.fragments.FragmentDetail;

public class ViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        FragmentDetail fragmentDetail= FragmentDetail.newInstance(bundle);
        fragmentTransaction.add(R.id.view_frame_layout,fragmentDetail);
        fragmentTransaction.commit();
    }
}
