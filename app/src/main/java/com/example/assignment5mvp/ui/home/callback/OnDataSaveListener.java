package com.example.assignment5mvp.ui.home.callback;

import com.example.assignment5mvp.data.model.Student;

import java.util.ArrayList;

public interface OnDataSaveListener {
    void onDataSavedSuccess(final boolean isAddStudent,Student student);

    void onDataSavedError(final boolean isAddStudent);

    void onDeleteSuccess();

    void onDeleteError();

    void onFetchStudentList(final ArrayList<Student> studentArrayList);
}
