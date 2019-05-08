package com.example.assignment2;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class LoginActivity extends AppCompatActivity {

//regex for email validation
    public final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9+._%-+]{1,256}" +
                    "@" +
                    "[a-zA-Z0-9][a-zA-Z0-9-]{0,64}" +
                    "(" +
                    "." +
                    "[a-zA-Z0-9][a-zA-Z0-9-]{0,25}" +
                    ")+"
    );
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
       Button button =(Button)findViewById(R.id.login_btn);
       button.setOnClickListener(new View.OnClickListener() {
            @Override
           public void onClick(View v) {          //to hide the keyboard on button click
                try {
                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    emailValidation(v);

                } catch (Exception e) {

                }

            }




        });
        ImageView image = (ImageView) findViewById(R.id.login_iv_view);
       final EditText password=(EditText)findViewById(R.id.login_et_paswd);
        image .setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) { //to toggle password entered by user

                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        password.setInputType(InputType.TYPE_CLASS_TEXT);
                        break;
                    case MotionEvent.ACTION_UP:
                        password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        break;
                }
                return true;
            }
        });

    }
    public void openRegisterActivity(View view)
    {
        TextView textView =(TextView)findViewById(R.id.login_tv_register);
        Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(intent);
    }
   public void emailValidation(View view) {
       View parentLayout = findViewById(android.R.id.content);
       EditText editText = (EditText) findViewById(R.id.login_et_email);

       String email = editText.getText().toString().trim();
       EditText editText1=findViewById(R.id.login_et_paswd);
       String passwd=editText1.getText().toString();
       if(email.length()==0)
       {
           Snackbar snackbar = Snackbar
                   .make(parentLayout, "Enter your email", Snackbar.LENGTH_LONG);
           snackbar.show();
       }
else {
           if (checkEmail(email) && passwd.length() == 0) {

               Snackbar snackbar = Snackbar
                       .make(parentLayout, "Enter your password", Snackbar.LENGTH_LONG);
               snackbar.show();


           } else if (checkEmail(email) &&  passwd.length()!=0){
               Snackbar snackbar = Snackbar
                       .make(parentLayout, "Logged in successfully", Snackbar.LENGTH_LONG);
               snackbar.show();

           }
           else

           {
               Snackbar snackbar = Snackbar
                       .make(parentLayout, "Invalid Email", Snackbar.LENGTH_LONG);
               snackbar.show();
           }
       }
   }



private boolean checkEmail(String email) {
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
        }
        }




