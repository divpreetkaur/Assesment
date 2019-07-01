package com.example.assignment5mvp.ui.home.detailfragment;

import com.example.assignment5mvp.data.db.DataBaseHandler;
import com.example.assignment5mvp.data.model.Student;
import com.example.assignment5mvp.ui.home.callback.OnDataSaveListener;

public interface DetailFragmentInteractor {
    void onSuccess(DataBaseHandler dataBaseHandler,String rollno,String actionType);
    void onFailure();
    void instantiateListener(DetailFragmentPresenter detailFragmentPresenter, OnDataSaveListener onDataSaveListener);
    void performDbOperation(DataBaseHandler dataBaseHandler,String service, Student student,String actionType);
}
