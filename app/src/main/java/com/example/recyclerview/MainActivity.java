package com.example.recyclerview;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayList<user> arrayList = new ArrayList<>();


        arrayList.add(new user("Divpreet", "20", "456789"));
        arrayList.add(new user("Siddharth", "20", "678965"));
        arrayList.add(new user("Khushi", "21", "9087654"));
        arrayList.add(new user("Bhimanshu", "20", "78956"));
        arrayList.add(new user("Sahil", "20", "234598"));
        arrayList.add(new user("Divya", "20", "78654"));
        arrayList.add(new user("Kriti", "20", "45679"));
        arrayList.add(new user("Komal", "20", "87654"));
        arrayList.add(new user("Avni", "20", "90876"));
        arrayList.add(new user("Kartik", "20", "32456"));
        arrayList.add(new user("Karan", "20", "54321"));

        recyclerView = (RecyclerView) findViewById(R.id.recycler_View);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new MyAdapter(arrayList);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

// set the adapter
        recyclerView.setAdapter(mAdapter);


        mAdapter.notifyDataSetChanged();

    }
}

