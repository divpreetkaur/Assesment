package com.example.assignment5.callback;

import com.example.assignment5.model.Student;

import java.util.ArrayList;

public interface OnDataSavedListener {

    void onDataSavedSuccess(final boolean isAddStudent,Student student);

    void onDataSavedError(final boolean isAddStudent);

    void onDeleteSuccess();

    void onDeleteError();

    void onFetchStudentList(final ArrayList<Student> studentArrayList);

}
