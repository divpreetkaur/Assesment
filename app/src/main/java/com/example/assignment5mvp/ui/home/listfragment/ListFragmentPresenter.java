package com.example.assignment5mvp.ui.home.listfragment;

import com.example.assignment5mvp.data.db.DataBaseHandler;
import com.example.assignment5mvp.data.model.Student;

public interface ListFragmentPresenter {
  void performDbOperation(Student student, String actionType, DataBaseHandler dataBaseHandler);
}
