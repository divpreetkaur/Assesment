package com.example.assignment5mvp.data.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.example.assignment5mvp.constant.Constants;
import com.example.assignment5mvp.data.db.DataBaseHandler;
import com.example.assignment5mvp.data.db.DatabaseUtil;
import com.example.assignment5mvp.data.model.Student;

import java.util.ArrayList;

public class BackgroundIntentService extends IntentService {
    private Constants constants=new Constants();
    private Student student;
    private ArrayList<Student> mArrayList=new ArrayList<>();


    public BackgroundIntentService() {
        super("intent_service_thread");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        DataBaseHandler dataBaseHandler = DatabaseUtil.getDataBase(getApplicationContext());
        boolean isSuccess=false;
        if(intent.getStringExtra(constants.ACTION_KEY).equals(constants.ADD)) {
            String name = intent.getStringExtra(constants.NAME_KEY);
            String rollno = intent.getStringExtra(constants.ROLLNO_KEY);
            String cls = intent.getStringExtra(constants.CLASS_KEY);
            student = new Student(name, rollno, cls);
            isSuccess = dataBaseHandler.insertData(student);
        }

        else if(intent.getStringExtra(constants.ACTION_KEY).equals(constants.EDIT))
        {
            String name = intent.getStringExtra(constants.NAME_KEY);
            String rollno = intent.getStringExtra(constants.ROLLNO_KEY);
            String cls = intent.getStringExtra(constants.CLASS_KEY);
            student = new Student(name, rollno, cls);
            isSuccess=dataBaseHandler.updateData(student);
        }
        else if(intent.getStringExtra(constants.ACTION_KEY).equals(constants.DELETE))
        {
            String rollno = intent.getStringExtra(constants.ROLLNO_KEY);
            isSuccess=dataBaseHandler.deleteData(rollno);
        }
        else if(intent.getStringExtra(constants.ACTION_KEY).equals(constants.READ_OPERATION))
        {
            mArrayList=dataBaseHandler.getListElements();
            isSuccess=true;
        }
        //sending broadcast
        intent.setAction(constants.BROADCAST_ACTION);
        String actionType = intent.getStringExtra(constants.ACTION_KEY);
        //if data is successfully added in database
        if (isSuccess && (actionType.equals(constants.EDIT)) || actionType.equals(constants.ADD) || actionType.equals(constants.DELETE) ){
            intent.putExtra(constants.IS_SUCCESS,constants.TRUE);
            intent.putExtra(constants.ACTION_KEY,actionType);
            LocalBroadcastManager.getInstance(getBaseContext()).sendBroadcast(intent);
        } else if (isSuccess && actionType.equals(constants.READ_OPERATION)) {
            intent.putExtra(constants.IS_SUCCESS,constants.TRUE);
            intent.putParcelableArrayListExtra(constants.ARRAY_LIST, mArrayList);
            intent.putExtra(constants.ACTION_KEY,actionType);
            LocalBroadcastManager.getInstance(getBaseContext()).sendBroadcast(intent);

        }
        //if data is not added in database
        else if(!isSuccess)
        {
            intent.putExtra(constants.IS_SUCCESS,constants.FALSE);
            intent.putExtra(constants.ACTION_KEY,actionType);
            LocalBroadcastManager.getInstance(getBaseContext()).sendBroadcast(intent);
        }

    }
}
