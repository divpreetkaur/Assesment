package com.example.assignment5mvp.ui.home.listfragment;

import android.util.Log;

import com.example.assignment5mvp.data.db.DataBaseHandler;
import com.example.assignment5mvp.data.model.Student;
import com.example.assignment5mvp.data.service.BackgroundTask;
import com.example.assignment5mvp.ui.home.callback.OnDataSaveListener;

import java.util.ArrayList;

public class ListFragmentInteractorImpl implements ListFragmentInteractor,OnDataSaveListener {
    private ListFragmentPresenter listFragmentPresenter;
    private OnDataSaveListener onDataSaveListener;

   public ListFragmentInteractorImpl()
   {

   }



    @Override
    public void performDbOperation(Student student, String actionType, DataBaseHandler dataBaseHandler) {
        BackgroundTask backgroundTask=new BackgroundTask(dataBaseHandler,actionType,this);
        backgroundTask.execute(student);

    }

    @Override
    public void instantiateListener(ListFragmentPresenter listFragmentPresenter, OnDataSaveListener onDataSaveListener) {
         this.listFragmentPresenter=listFragmentPresenter;
         this.onDataSaveListener=onDataSaveListener;
    }

    @Override
    public void onDataSavedSuccess(boolean isAddStudent, Student student) {


    }

    @Override
    public void onDataSavedError(boolean isAddStudent) {

    }

    @Override
    public void onDeleteSuccess() {
           onDataSaveListener.onDeleteSuccess();
    }

    @Override
    public void onDeleteError() {
       onDataSaveListener.onDeleteError();

    }

    @Override
    public void onFetchStudentList(ArrayList<Student> studentArrayList) {
       onDataSaveListener.onFetchStudentList(studentArrayList);

    }
}
