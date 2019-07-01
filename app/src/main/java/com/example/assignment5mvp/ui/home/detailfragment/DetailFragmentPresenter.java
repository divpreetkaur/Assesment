package com.example.assignment5mvp.ui.home.detailfragment;

import com.example.assignment5mvp.data.db.DataBaseHandler;
import com.example.assignment5mvp.data.model.Student;

public interface DetailFragmentPresenter {
  void validateText(String name, String rollno, String cls,DataBaseHandler dataBaseHandler,String actionType);
  void onSuccess();
  void onFailure();
  void performDbOperation(DataBaseHandler dataBaseHandler,String service, Student student,String actionType);

}
