package com.example.assignment5mvp.ui.home.callback;

import com.example.assignment5mvp.data.model.Student;

import java.util.ArrayList;

public interface DetailFragmentClickListener {
   void myClick(String actionType, Student student);
   void setService(String service);
   void onDBoperationError(String actionType);
   void fetchDBList(ArrayList<Student> arrayList);
}
