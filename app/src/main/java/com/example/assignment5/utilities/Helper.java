package com.example.assignment5.utilities;

import android.content.Context;
import android.widget.Toast;

import com.example.assignment5.R;

public class Helper {
 static Constants constants=new Constants();
    public static boolean validate(Context context, String name, String rollno, String cls) {

        if (name.length() == 0) {
           Toast.makeText(context, constants.ENTER_NAME, Toast.LENGTH_SHORT).show();
            return false;
        } else if (rollno.length() == 0) {
            Toast.makeText(context, constants.ENTER_ROLLNO, Toast.LENGTH_SHORT).show();
            return false;
        } else if (cls.length() == 0) {
            Toast.makeText(context, constants.ENTER_CLASS, Toast.LENGTH_SHORT).show();
            return false;
        } else
            return true;


    }
}
