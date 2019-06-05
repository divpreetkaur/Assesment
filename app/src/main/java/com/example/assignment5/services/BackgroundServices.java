package com.example.assignment5.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;

import com.example.assignment5.database.DataBaseHelper;
import com.example.assignment5.utilities.Constants;

public class BackgroundServices extends Service {

    DataBaseHelper dataBaseHelper;
    Constants constants=new Constants();
    public BackgroundServices()
    {

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
      @Override
      public void onCreate()
      {
          super.onCreate();

      }
      @Override
     public int onStartCommand(Intent intent, int flags, int startId) {
       dataBaseHelper=new DataBaseHelper(getApplicationContext());
       if(intent.getStringExtra(constants.ACTION_KEY).equals(constants.ADD)) {
           String name = intent.getStringExtra(constants.NAME_KEY);
           String rollno = intent.getStringExtra(constants.ROLLNO_KEY);
           String cls = intent.getStringExtra(constants.CLASS_KEY);
           dataBaseHelper.insertData(name, rollno, cls);
       }
       else if(intent.getStringExtra(constants.ACTION_KEY).equals(constants.EDIT))
       {
           String name = intent.getStringExtra(constants.NAME_KEY);
           String rollno = intent.getStringExtra(constants.ROLLNO_KEY);
           String cls = intent.getStringExtra(constants.CLASS_KEY);
           String oldRollNumber=intent.getStringExtra(constants.OLDROLLNUMBER);
           dataBaseHelper.updateData(name,rollno,cls,oldRollNumber);
       }
       else if(intent.getStringExtra(constants.ACTION_KEY).equals(constants.DELETE))
       {
           String name = intent.getStringExtra(constants.NAME_KEY);
           String rollno = intent.getStringExtra(constants.ROLLNO_KEY);
           String cls = intent.getStringExtra(constants.CLASS_KEY);
           dataBaseHelper.deleteData(name,rollno,cls);
       }  //sending broadcast
          intent.setAction(constants.BROADCAST_ACTION);
          String echoMessage = constants.MESSAGE;
          LocalBroadcastManager.getInstance(getBaseContext()).sendBroadcast(intent.putExtra(constants.MESSAGE,echoMessage));
          return START_NOT_STICKY;
      }

    }
