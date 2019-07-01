package com.example.assignment5mvp;

import android.app.Application;

import com.example.assignment5mvp.data.db.DataBaseHandler;
import com.example.assignment5mvp.data.db.DatabaseUtil;

public class MyApplication extends Application {

    DataBaseHandler dataBaseHandler=new DataBaseHandler(this);



}
