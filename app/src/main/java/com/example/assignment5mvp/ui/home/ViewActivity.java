package com.example.assignment5mvp.ui.home;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.assignment5mvp.R;
import com.example.assignment5mvp.constant.Constants;
import com.example.assignment5mvp.ui.home.detailfragment.DetailFragment;

public class ViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        DetailFragment detailFragment= DetailFragment.newInstance(bundle);
        fragmentTransaction.add(R.id.view_frame_layout,detailFragment);
        fragmentTransaction.commit();
    }
}
