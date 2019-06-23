package com.example.assignment5.services;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.assignment5.R;
import com.example.assignment5.activity.HomeActivity;
import com.example.assignment5.adapters.StudentAdapter;
import com.example.assignment5.callback.OnDataSavedListener;
import com.example.assignment5.database.DataBaseHelper;
import com.example.assignment5.fragments.FragmentList;
import com.example.assignment5.model.Student;
import com.example.assignment5.utilities.Constants;

import java.util.ArrayList;

public class BackgroundTask extends AsyncTask<Student,Void,Boolean> {
    Constants constants = new Constants();
    private String mActionType;
    private Student mStudent;
    Context context;
    private OnDataSavedListener mListener;
    private ArrayList<Student> mArrayList;


    public BackgroundTask(Context context,final OnDataSavedListener listener,final String actionType) {
        this.context = context;
        this.mListener=listener;
        this.mActionType=actionType;


    }

    @Override
    protected Boolean doInBackground(Student... student) {
        Boolean isSuccess=false;
        mStudent=student[0];
       DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
        if (mActionType.equals(constants.ADD)) {
             isSuccess=dataBaseHelper.insertData(mStudent);
        } else if (mActionType.equals(constants.EDIT)) {
            isSuccess=dataBaseHelper.updateData(mStudent);
        }
          else if(mActionType.equals(constants.DELETE)) {
            isSuccess=dataBaseHelper.deleteData(student[0].getRollno());
        }
          else if(mActionType.equals(constants.READ_OPERATION))
        {
            mArrayList=dataBaseHelper.getListElements();
              isSuccess=true;


        }
        return isSuccess;
    }


    @Override
    protected void onPostExecute(Boolean isSuccess) {

         if(isSuccess) {
             if (mActionType.equals(constants.ADD)
                     || mActionType.equals(constants.EDIT)) {
                 mListener.onDataSavedSuccess(mActionType.equals(constants.ADD), mStudent);

             } else if (mActionType.equals(constants.DELETE)) {

                 mListener.onDeleteSuccess();
             } else if (mActionType.equals(constants.READ_OPERATION)) {
                 mListener.onFetchStudentList(mArrayList);
             }
         }
         else if(!isSuccess)
         {
             if (mActionType.equals(constants.ADD)
                     || mActionType.equals(constants.EDIT)) {

                 mListener.onDataSavedError(mActionType.equals(constants.ADD));

             } else if (mActionType.equals(constants.DELETE)) {

                 mListener.onDeleteError();

             }
         }




    }
}
