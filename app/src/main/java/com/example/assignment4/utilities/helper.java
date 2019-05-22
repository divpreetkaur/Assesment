package com.example.assignment4.utilities;

import android.content.Context;
import android.widget.Toast;

import com.example.assignment4.R;

import java.util.ArrayList;

public class helper {

    Context context;
    int i;

    ArrayList<String> rollnoArrayList = new ArrayList<>();
    String temprollno;

   public static boolean validate( Context context,String name, String rollno, String cls) {

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
}
