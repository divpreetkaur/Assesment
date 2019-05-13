package com.example.recyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class my_text_view extends AppCompatActivity {
  int count_likes=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_text_view);
    }
    void count(View view)
    {
        TextView textView =(TextView)findViewById(R.id.text_tv_count);
        count_likes++;
        textView.setText(" "+count_likes);
    }
}
