package com.example.assignment5mvp.ui.home.listfragment;

import com.example.assignment5mvp.data.db.DataBaseHandler;
import com.example.assignment5mvp.data.model.Student;
import com.example.assignment5mvp.ui.home.callback.OnDataSaveListener;

public interface ListFragmentInteractor {

    void performDbOperation(Student student, String actionType, DataBaseHandler dataBaseHandler);
    void instantiateListener(ListFragmentPresenter listFragmentPresenter, OnDataSaveListener onDataSaveListener);
}
