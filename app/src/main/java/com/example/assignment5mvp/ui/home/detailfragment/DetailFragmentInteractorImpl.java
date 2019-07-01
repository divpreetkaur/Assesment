package com.example.assignment5mvp.ui.home.detailfragment;

import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.example.assignment5mvp.constant.Constants;
import com.example.assignment5mvp.data.db.DataBaseHandler;
import com.example.assignment5mvp.data.model.Student;
import com.example.assignment5mvp.data.service.BackgroundTask;
import com.example.assignment5mvp.ui.home.callback.OnDataSaveListener;
import com.example.assignment5mvp.ui.home.listfragment.ListFragmentInteractor;

import java.util.ArrayList;

public class DetailFragmentInteractorImpl implements DetailFragmentInteractor, OnDataSaveListener {
   private DetailFragmentPresenter detailFragmentPresenter;
    private OnDataSaveListener onDataSaveListener;
    private Constants constants = new Constants();
    private String mRollno;
    private Student student;

    public DetailFragmentInteractorImpl() {

    }


    @Override
    public void onSuccess(DataBaseHandler dataBaseHandler, String rollno,String actionType) {
         //fetching the data from database for rollno validation
        mRollno = rollno;
        if(actionType.equals(constants.ADD)) {
            BackgroundTask backgroundTask = new BackgroundTask(dataBaseHandler, constants.READ_OPERATION, this);
            backgroundTask.execute(student);
        }
        else
        detailFragmentPresenter.onSuccess();


    }

    @Override
    public void onFailure() {

    }

    @Override
    public void instantiateListener(DetailFragmentPresenter detailFragmentPresenter, OnDataSaveListener onDataSaveListener) {
        this.detailFragmentPresenter = detailFragmentPresenter;
        this.onDataSaveListener = onDataSaveListener;
    }

    @Override
    public void performDbOperation(DataBaseHandler dataBaseHandler, String service, Student student, String actionType) {
        if (service.equals(constants.BACKGROUND_TASK)) {
            BackgroundTask backgroundTask = new BackgroundTask(dataBaseHandler, actionType, this);
            backgroundTask.execute(student);
        }
    }

    @Override
    public void onDataSavedSuccess(boolean isAddStudent, Student student) {

        onDataSaveListener.onDataSavedSuccess(isAddStudent, student);
    }

    @Override
    public void onDataSavedError(boolean isAddStudent) {
        onDataSaveListener.onDataSavedError(isAddStudent);

    }

    @Override
    public void onDeleteSuccess() {

    }

    @Override
    public void onDeleteError() {

    }
    //performing rollno validation
    @Override
    public void onFetchStudentList(ArrayList<Student> studentArrayList) {
            int flag=0;
            for(int i=0;i<studentArrayList.size();i++)
            {
                student=studentArrayList.get(i);
                if(student.getRollno().equals(mRollno))
                {
                    flag=1;
                    break;
                }
            }
            if(flag==0)
                detailFragmentPresenter.onSuccess();
            else
                detailFragmentPresenter.onFailure();
    }
    }
