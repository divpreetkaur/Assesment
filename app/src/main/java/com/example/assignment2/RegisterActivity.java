package com.example.assignment2;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);



    }

     public void onBackPressed(View view)
    {
       super.onBackPressed();
    }

    public void openLoginActivity(View view) {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivityForResult(intent, 0);
    }

    public void validate(View view)



    {
        View ParentLayout = findViewById(android.R.id.content);
        EditText editText_name = (EditText) findViewById(R.id.register_et_fullname);//fetching the edit texts
       EditText editText_occupation = (EditText)findViewById(R.id.register_et_occupation);
        EditText editText_gender = (EditText)findViewById(R.id.register_et_gender);
        EditText editText_usertype = (EditText)findViewById(R.id.register_et_usertype);
        String usertype=editText_usertype.getText().toString().trim(); //fetching edit text data
        String fullname = editText_name.getText().toString();
        String gender=editText_gender.getText().toString().toLowerCase();
        String occupation=editText_occupation.getText().toString().trim();
        if (fullname.length() == 0) {
            Snackbar snackbar = Snackbar.make(ParentLayout, "Enter name", Snackbar.LENGTH_LONG);
            snackbar.show();
        }
      else if(gender.length()==0)
        {
            Snackbar snackbar=Snackbar.make(ParentLayout,"Enter gender",Snackbar.LENGTH_LONG);
            snackbar.show();
        }
        else if(gender.equals("male")==false && gender.equals("female")==false)
        {
            Snackbar snackbar=Snackbar.make(ParentLayout,"Enter valid gender",Snackbar.LENGTH_LONG);
            snackbar.show();
        }
        else if(usertype.length()==0)
        {

            Snackbar snackbar=Snackbar.make(ParentLayout,"Enter usertype",Snackbar.LENGTH_LONG);
            snackbar.show();
        }

        else if(occupation.length()==0)
        {

            Snackbar snackbar=Snackbar.make(ParentLayout,"Enter occupation",Snackbar.LENGTH_LONG);
            snackbar.show();
        }
        else {
            Intent intent = new Intent(RegisterActivity.this, OTPActivity.class);
            startActivityForResult(intent, 0);

        }
    }


}
