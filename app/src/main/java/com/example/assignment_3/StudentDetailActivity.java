package com.example.assignment_3;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class StudentDetailActivity extends AppCompatActivity {
    EditText etName;
    EditText etRollno;
    EditText etClass;
    String name;
    String rollno;
    String cls;
    Button btnSubmit;
    Helper helper = new Helper();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int i;
        setContentView(R.layout.activity_student_detail);
        init();
        final Context context = getApplicationContext();
      //getting data by bundles

        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            name=extras.getString(getString(R.string.key_name));
            rollno=extras.getString(getString(R.string.key_rollno));
            cls=extras.getString(getString(R.string.key_class));
             if(extras.getString(getString(R.string.action)).equals(getString(R.string.show)))
                 setTextView(name,rollno,cls);
             else
                 setEditText(name,rollno,cls);
        }
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                       name = etName.getText().toString();
                       cls = etClass.getText().toString();
                       rollno = etRollno.getText().toString();
                    if (helper.validate(context, name, rollno, cls)) {
                        //sending back the activity result

                           Intent intent = new Intent();
                           intent.putExtra(getString(R.string.key_name), name);
                           intent.putExtra(getString(R.string.key_rollno), rollno);
                           intent.putExtra(getString(R.string.key_class), cls);
                           setResult(Activity.RESULT_OK, intent);
                           finish();
                       }
                   }

                //finishing activity


//

        });

    }

    //initialising views
    void init() {
        etName = (EditText) findViewById(R.id.detail_et_name);
        etRollno = (EditText) findViewById(R.id.detail_et_rollno);
        etClass = (EditText) findViewById(R.id.detail_et_class);
        btnSubmit = (Button) findViewById(R.id.detail_btn);

    }
    //converting edit text to text view
    void setTextView(String stuName,String stuRollno,String stuCls)
    {
         etName.setText(stuName);
         etRollno.setText(stuRollno);
         etClass.setText(stuCls);
         etName.setEnabled(false);
        etRollno.setEnabled(false);
        etClass.setEnabled(false);
        btnSubmit.setVisibility(View.INVISIBLE);


    }

    void setEditText(String stuName,String stuRollno,String stuCls)
    {
        etName.setText(stuName);
        etRollno.setText(stuRollno);
        etClass.setText(stuCls);
        btnSubmit.setText(getString(R.string.update));
    }
}



