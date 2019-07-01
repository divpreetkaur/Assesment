package com.example.assignment5mvp.ui.home.listfragment;

import android.util.Log;

import com.example.assignment5mvp.R;
import com.example.assignment5mvp.data.db.DataBaseHandler;
import com.example.assignment5mvp.data.model.Student;
import com.example.assignment5mvp.ui.home.callback.OnDataSaveListener;

import java.util.ArrayList;

public class ListFragmentPresenterImpl implements ListFragmentPresenter, OnDataSaveListener {
    private ListFragmentInteractor listFragmentInteractor;
    private ListFragmentView listFragmentView;
   private OnDataSaveListener onDataSaveListener;
    public ListFragmentPresenterImpl(ListFragmentView listFragmentView,ListFragmentInteractor listFragmentInteractor,OnDataSaveListener onDataSaveListener)
    {
      this.listFragmentInteractor=listFragmentInteractor;
      this.listFragmentView=listFragmentView;
      this.onDataSaveListener=onDataSaveListener;
      listFragmentInteractor.instantiateListener(this,this);
    }




    @Override
    public void performDbOperation(Student student, String actionType, DataBaseHandler dataBaseHandler) {
     listFragmentInteractor.performDbOperation(student,actionType,dataBaseHandler);

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
