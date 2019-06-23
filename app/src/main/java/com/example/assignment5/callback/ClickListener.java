package com.example.assignment5.callback;

import com.example.assignment5.model.Student;

import java.util.ArrayList;

public interface ClickListener {

        void onClick(Student student, String actionType);

        void setService(String service);

        void fetchDbList(ArrayList<Student> arrayList, String actionType);
}
