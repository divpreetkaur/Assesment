package com.example.assignment5mvp.data.service;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.assignment5mvp.constant.Constants;
import com.example.assignment5mvp.data.db.DataBaseHandler;
import com.example.assignment5mvp.data.model.Student;
import com.example.assignment5mvp.ui.home.callback.OnDataSaveListener;
import com.example.assignment5mvp.ui.home.detailfragment.DetailFragmentInteractorImpl;
import com.example.assignment5mvp.ui.home.detailfragment.DetailFragmentPresenterImpl;

import java.util.ArrayList;

public class BackgroundTask extends AsyncTask<Student,Void,Boolean> {


   private Constants constants = new Constants();
    private String mActionType;
    private Student mStudent;
   private OnDataSaveListener mListener;
    private ArrayList<Student> mArrayList;
    private DataBaseHandler dataBaseHandler;

    public BackgroundTask(DataBaseHandler dataBaseHandler,final String actionType,OnDataSaveListener onDataSaveListener) {

        this.dataBaseHandler=dataBaseHandler;
        this.mActionType=actionType;
        mListener=onDataSaveListener;
        mArrayList=new ArrayList<>();


    }


    @Override
    protected Boolean doInBackground(Student... student) {
        Boolean isSuccess=false;
        mStudent=student[0];

        if (mActionType.equals(constants.ADD)) {
            isSuccess=dataBaseHandler.insertData(mStudent);
        } else if (mActionType.equals(constants.EDIT)) {
            isSuccess=dataBaseHandler.updateData(mStudent);
        }
        else if(mActionType.equals(constants.DELETE)) {
            isSuccess=dataBaseHandler.deleteData(student[0].getRollno());
        }
        else if(mActionType.equals(constants.READ_OPERATION))
        {
            mArrayList=dataBaseHandler.getListElements();
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


