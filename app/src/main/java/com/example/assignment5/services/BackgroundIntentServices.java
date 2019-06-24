package com.example.assignment5.services;

import android.app.IntentService;
import android.content.Intent;
import android.provider.SyncStateContract;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.example.assignment5.database.DataBaseHelper;
import com.example.assignment5.model.Student;
import com.example.assignment5.utilities.Constants;

import java.util.ArrayList;

public class BackgroundIntentServices extends IntentService {


    private DataBaseHelper dataBaseHelper;
    private Constants constants=new Constants();
    private Student student;
    private ArrayList<Student> mArrayList=new ArrayList<>();
    public BackgroundIntentServices() {

        super("intent_service");
    }
   //performing database operations
    @Override
    protected void onHandleIntent(Intent intent) {
        dataBaseHelper=new DataBaseHelper(getApplicationContext());
        boolean isSuccess=false;
        if(intent.getStringExtra(constants.ACTION_KEY).equals(constants.ADD)) {
            String name = intent.getStringExtra(constants.NAME_KEY);
            String rollno = intent.getStringExtra(constants.ROLLNO_KEY);
            String cls = intent.getStringExtra(constants.CLASS_KEY);
            student = new Student(name, rollno, cls, constants.OLD_ROLL_NO);
            isSuccess = dataBaseHelper.insertData(student);
        }

        else if(intent.getStringExtra(constants.ACTION_KEY).equals(constants.EDIT))
        {
            String name = intent.getStringExtra(constants.NAME_KEY);
            String rollno = intent.getStringExtra(constants.ROLLNO_KEY);
            String cls = intent.getStringExtra(constants.CLASS_KEY);
            String oldRollNUmber=intent.getStringExtra(constants.OLDROLLNUMBER);
            student = new Student(name, rollno, cls, oldRollNUmber);
          isSuccess=dataBaseHelper.updateData(student);
        }
        else if(intent.getStringExtra(constants.ACTION_KEY).equals(constants.DELETE))
        {
            String rollno = intent.getStringExtra(constants.ROLLNO_KEY);
            isSuccess=dataBaseHelper.deleteData(rollno);
        }
        else if(intent.getStringExtra(constants.ACTION_KEY).equals(constants.READ_OPERATION))
        {
            mArrayList=dataBaseHelper.getListElements();
            isSuccess=true;
        }
        //sending broadcast
        intent.setAction(constants.BROADCAST_ACTION);
        String actionType = intent.getStringExtra(constants.ACTION_KEY);
        if (isSuccess && (actionType.equals(constants.ADD) || actionType.equals(constants.EDIT))) {
            intent.putExtra(constants.IS_SUCCESS,constants.TRUE);
            intent.putExtra(constants.ACTION_KEY,actionType);
            LocalBroadcastManager.getInstance(getBaseContext()).sendBroadcast(intent);
        } else if (isSuccess && actionType.equals(constants.READ_OPERATION)) {
            intent.putExtra(constants.IS_SUCCESS,constants.TRUE);
            intent.putParcelableArrayListExtra(constants.ARRAY_LIST, mArrayList);
            intent.putExtra(constants.ACTION_KEY,actionType);
            LocalBroadcastManager.getInstance(getBaseContext()).sendBroadcast(intent);

        }
        else if(!isSuccess)
        {
            intent.putExtra(constants.IS_SUCCESS,constants.FALSE);
            intent.putExtra(constants.ACTION_KEY,actionType);
            LocalBroadcastManager.getInstance(getBaseContext()).sendBroadcast(intent);
        }
    }
}
