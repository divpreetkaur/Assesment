package com.example.assignment_3;
import android.content.Context;
import android.text.GetChars;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Helper {

    Context context;
    int i;
    Student student = new Student();
    ArrayList<String> rollnoArrayList = new ArrayList<>();
    String temprollno;

    boolean validate(Context context, String name, String rollno, String cls) {

        if (name.length() == 0) {
            Toast.makeText(context, context.getString(R.string.string_name), Toast.LENGTH_SHORT).show();
            return false;
        } else if (rollno.length() == 0) {
            Toast.makeText(context, context.getString(R.string.string_rollno), Toast.LENGTH_SHORT).show();
            return false;
        } else if (cls.length() == 0) {
            Toast.makeText(context, context.getString(R.string.string_cls), Toast.LENGTH_SHORT).show();
            return false;
        } else
            return true;


    }

    void compareName(ArrayList<Student> arrayList) {
        Collections.sort(arrayList, new Comparator<Student>() {
            @Override
            public int compare(Student student1, Student student2) {
                return student1.getName().compareTo(student2.getName());
            }
        });
    }

    void compareId(ArrayList<Student> arrayList) {
        Collections.sort(arrayList, new Comparator<Student>() {
            @Override
            public int compare(Student student1, Student student2) {
                int num1 = Integer.parseInt(student1.getRollno());
                int num2 = Integer.parseInt(student2.getRollno());
                if (num1 > num2)
                    return 1;
                else
                    return -1;
            }
        });
    }



    }

