package com.example.assignment5.services;

import android.app.IntentService;
import android.content.Intent;
import android.provider.SyncStateContract;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.example.assignment5.database.DataBaseHelper;
import com.example.assignment5.utilities.Constants;

public class BackgroundIntentServices extends IntentService {


    DataBaseHelper dataBaseHelper;
    Constants constants=new Constants();

    public BackgroundIntentServices() {

        super("intent_service");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        dataBaseHelper=new DataBaseHelper(getApplicationContext());
        if(intent.getStringExtra(constants.ACTION_KEY).equals(constants.ADD)) {
            dataBaseHelper.insertData(intent.getStringExtra(constants.NAME_KEY), intent.getStringExtra(constants.ROLLNO_KEY),
                    intent.getStringExtra(constants.CLASS_KEY));
        }
        else if(intent.getStringExtra(constants.ACTION_KEY).equals(constants.EDIT))
        {
            String name = intent.getStringExtra(constants.NAME_KEY);
            String rollno = intent.getStringExtra(constants.ROLLNO_KEY);
            String cls = intent.getStringExtra(constants.CLASS_KEY);
            String oldRollNUmber=intent.getStringExtra(constants.OLDROLLNUMBER);
            dataBaseHelper.updateData(name,rollno,cls,oldRollNUmber);
        }
        else if(intent.getStringExtra(constants.ACTION_KEY).equals(constants.DELETE))
        {
            String name = intent.getStringExtra(constants.NAME_KEY);
            String rollno = intent.getStringExtra(constants.ROLLNO_KEY);
            String cls = intent.getStringExtra(constants.CLASS_KEY);
            dataBaseHelper.deleteData(name,rollno,cls);
        }
        //sending broadcast
        intent.setAction(constants.BROADCAST_ACTION);
        String echoMessage =constants.MESSAGE;
        LocalBroadcastManager.getInstance(getBaseContext()).sendBroadcast(intent.putExtra(constants.MESSAGE,echoMessage));


    }
}
