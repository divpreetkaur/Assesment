package com.example.assignment2;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

public class OTPActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        Button button = (Button) findViewById(R.id.otp_btn_resend);
        Button button1 = (Button) findViewById(R.id.otp_btn_submit);
        button.setOnClickListener(this);
        button1.setOnClickListener(this);
        final EditText editText=(EditText)findViewById(R.id.otp_et_1);
        final EditText editText1=(EditText)findViewById(R.id.otp_et_2);
        final EditText editText2=(EditText)findViewById(R.id.otp_et_3);
        final EditText editText3=(EditText)findViewById(R.id.otp_et_4);
        editText.addTextChangedListener(new TextWatcher() { //to change the focus of edit text
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (editText.getText().toString().trim().length() == 1)     //size as per your requirement
                {
                    editText1.requestFocus();
                }

            }
         public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }
            public void afterTextChanged(Editable s) {

           }
        });
        editText1.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (editText1.getText().toString().trim().length() == 1)     //size as per your requirement
                {
                    editText2.requestFocus();
                }
                else if(editText1.getText().toString().trim().length()==0)
                {
                    editText.requestFocus();
                }
            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {

            }
            public void afterTextChanged(Editable s) {

            }
        });
        editText2.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (editText2.getText().toString().trim().length() == 1)
                {
                    editText3.requestFocus();
                }
                else if(editText2.getText().toString().trim().length()==0)
                {
                    editText1.requestFocus();
                }
            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {

            }
            public void afterTextChanged(Editable s) {

            }
        });
        editText3.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (editText3.getText().toString().trim().length() == 0)
                {
                    editText2.requestFocus();
                }

            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {

            }
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onClick(View view)// to shut down keyboard on button click
    {
        switch(view.getId())
        {
            case R.id.otp_btn_resend:
                try {
                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    resend(view);

                } catch (Exception e) {

                }
                break;
            case R.id.otp_btn_submit:
                try {
                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    submit(view);

                } catch (Exception e) {

                }
                break;



        }
    }
    void resend(View view)
    {   View ParentLayout=findViewById(android.R.id.content);
        Button button =(Button)findViewById(R.id.otp_btn_resend);
        Snackbar snackbar = Snackbar.make(ParentLayout," OTP IS SENT AGAIN",Snackbar.LENGTH_LONG);
        snackbar.show();
        EditText editText=(EditText)findViewById(R.id.otp_et_1);
        EditText editText1=(EditText)findViewById(R.id.otp_et_2);
        EditText editText2=(EditText)findViewById(R.id.otp_et_3);
        EditText editText3=(EditText)findViewById(R.id.otp_et_4);
        editText.getText().clear();//to clear the edit text data on button click
        editText1.getText().clear();
        editText2.getText().clear();
        editText3.getText().clear();
        editText.requestFocus();
    }
    void submit(View view)
    {View ParentLayout = findViewById(android.R.id.content);
        EditText editText=(EditText)findViewById(R.id.otp_et_1);
        String number=editText.getText().toString();
        EditText editText1=(EditText)findViewById(R.id.otp_et_2);
        String number1=editText1.getText().toString();
        EditText editText2=(EditText)findViewById(R.id.otp_et_3);
        String number2=editText2.getText().toString();
        EditText editText3=(EditText)findViewById(R.id.otp_et_4);
        String number3=editText3.getText().toString();
        if(number.isEmpty() || number1.isEmpty() || number2.isEmpty() || number3.isEmpty())
        {
            Snackbar snackbar = Snackbar.make(ParentLayout,"WRONG OTP",Snackbar.LENGTH_LONG);
            snackbar.show();
        }
        else
        {
            Intent intent = new Intent(OTPActivity.this,LoginActivity.class);
            startActivityForResult(intent,0);
        }
    }
}
