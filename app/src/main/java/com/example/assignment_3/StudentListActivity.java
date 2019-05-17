package com.example.assignment_3;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class StudentListActivity extends AppCompatActivity implements StudentAdapter.MyClickListener   {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    StudentAdapter studentAdapter;
    String name;
    String rollno;
    String cls;
    Helper helper;
    String tempName;
    String tempRollno;
    String tempClass;
    int getPosition;
    ArrayList<String>rollnoList=new ArrayList<>();
    TextView textView;
    Student student=new Student();
     RecyclerView.LayoutManager newLayoutManager;
     StudentDetailActivity studentDetailActivity;
    GridLayoutManager gridLayoutManager;
    ArrayList<Student> arrayList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_View);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        gridLayoutManager = new GridLayoutManager(getApplicationContext(),3);
        recyclerView.setLayoutManager(layoutManager);
        studentAdapter = new StudentAdapter(arrayList);
        recyclerView.setAdapter(studentAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        studentAdapter.setOnItemClickListener(this);
        helper=new Helper();
        if(arrayList.size()==0)
        {
            textView=(TextView)findViewById(R.id.list_tv);
            textView.setVisibility(View.VISIBLE);
        }

    }
    //defining alert dialogue box functionality
    @Override
    public void onItemClick(final int position, View v) {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.options))
                .setPositiveButton(getString(R.string.delete), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //on clicking delete function

                        studentAdapter.deleteItem(position);
                    }
                })
                .setNegativeButton(getString(R.string.edit), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //on clicking edit option
                       Student student=arrayList.get(position);

                         tempName=student.getName();
                         tempRollno=student.getRollno();
                         tempClass=student.getClasses();
                        getPosition=position;
                        Bundle bundle = new Bundle();
                        bundle.putString(getString(R.string.key_name),tempName);
                        bundle.putString(getString(R.string.key_rollno),tempRollno);
                        bundle.putString(getString(R.string.key_class),tempClass);
                        bundle.putString(getString(R.string.action),getString(R.string.edit));
                        Intent intent = new Intent(StudentListActivity.this,StudentDetailActivity.class);
                        intent.putExtras(bundle);
                        startActivityForResult(intent,1);
                    }
                })
                .setNeutralButton(getString(R.string.show), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //on clicking view option
                        Student student=arrayList.get(position);
                        tempName=student.getName();
                        tempRollno=student.getRollno();
                        tempClass=student.getClasses();
                        Bundle bundle = new Bundle();
                        bundle.putString(getString(R.string.key_name),tempName);
                        bundle.putString(getString(R.string.key_rollno),tempRollno);
                        bundle.putString(getString(R.string.key_class),tempClass);
                        bundle.putString(getString(R.string.action),getString(R.string.show));
                        Intent intent = new Intent(StudentListActivity.this,StudentDetailActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                })

                .show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                name = data.getStringExtra(getString(R.string.key_name));
                rollno = data.getStringExtra(getString(R.string.key_rollno));
                //validating rollno.
                if (rollnoList.contains(rollno)) {
                    Toast.makeText(getApplicationContext(),getString(R.string.validata_rollno), Toast.LENGTH_SHORT).show();
                    openStudentDetail(null);
                } else {
                    rollnoList.add(rollno);
                    cls = data.getStringExtra(getString(R.string.key_class));
                    arrayList.add(new Student(name, rollno, cls));

                    if (arrayList.size() > 0) {
                        textView.setVisibility(View.INVISIBLE);
                    }
                }


            }
        }
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                arrayList.remove(getPosition);
                studentAdapter.notifyItemRemoved(getPosition);
                name = data.getStringExtra(getString(R.string.key_name));
                rollno = data.getStringExtra(getString(R.string.key_rollno));

                if (rollnoList.contains(rollno)) {
                    Toast.makeText(getApplicationContext(),getString(R.string.validata_rollno), Toast.LENGTH_SHORT).show();
                    openStudentDetail(null);

                }
                 else {
                     rollnoList.add(rollno);
                    cls = data.getStringExtra(getString(R.string.key_class));

                    arrayList.add(getPosition, new Student(name, rollno, cls));

                }
            }

        }
    }


    void openStudentDetail(View view) {
        Intent intent = new Intent(StudentListActivity.this, StudentDetailActivity.class);
        startActivityForResult(intent, 2);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the actionbar
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //to switch the layout of recycler view
            case R.id.action_mainMenu:
                 newLayoutManager = recyclerView.getLayoutManager();
                if(newLayoutManager instanceof GridLayoutManager) {
                    recyclerView.setLayoutManager(layoutManager);


                } else if(newLayoutManager instanceof LinearLayoutManager) {
                    recyclerView.setLayoutManager(gridLayoutManager);

                }

                return true;


            case R.id.action_mainMenu2:
                //sorting recycler view by name
                helper.compareName(arrayList);

                studentAdapter.notifyDataSetChanged();

                return true;

            case R.id.action_mainMenu3:
                //sorting recycler view by roll no.
                helper.compareId(arrayList);

                studentAdapter.notifyDataSetChanged();


                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

}

