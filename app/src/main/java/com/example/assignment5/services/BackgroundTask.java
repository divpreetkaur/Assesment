package com.example.assignment5.services;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.assignment5.activity.HomeActivity;
import com.example.assignment5.adapters.StudentAdapter;
import com.example.assignment5.database.DataBaseHelper;
import com.example.assignment5.fragments.FragmentList;
import com.example.assignment5.model.Student;
import com.example.assignment5.utilities.Constants;

import java.util.ArrayList;

public class BackgroundTask extends AsyncTask<String,Void,Boolean> {
    Constants constants = new Constants();
    Context context;


    public BackgroundTask(Context context) {
        this.context = context;
    }


    @Override
    protected Boolean doInBackground(String... params) {
        Boolean answer=false;
        String method = params[0];
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
        if (method.equals(constants.ADD)) {
            String name = params[1];
            String rollno = params[2];
            String cls = params[3];
             answer=dataBaseHelper.insertData(name, rollno, cls);
        } else if (method.equals(constants.EDIT)) {
            String name = params[1];
            String rollno = params[2];
            String cls = params[3];
            String oldRollNumber=params[4];
            answer=dataBaseHelper.updateData(name, rollno, cls,oldRollNumber);
        }
          else if(method.equals(constants.DELETE))

        { String name = params[1];
            String rollno = params[2];
            String cls = params[3];
            dataBaseHelper.deleteData(name,rollno,cls);


        }
        return answer;
    }


    @Override
    protected void onPostExecute(Boolean answer) {

         if(answer==true)
         {
             Toast.makeText(context,constants.ENTERED_DATA,Toast.LENGTH_LONG).show();
         }

    }
}
