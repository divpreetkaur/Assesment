package com.example.assignment5.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.assignment5.R;
import com.example.assignment5.activity.ViewActivity;
import com.example.assignment5.adapters.StudentAdapter;
import com.example.assignment5.database.DataBaseHelper;
import com.example.assignment5.model.Student;
import com.example.assignment5.services.BackgroundIntentServices;
import com.example.assignment5.services.BackgroundServices;
import com.example.assignment5.services.BackgroundTask;
import com.example.assignment5.utilities.Constants;

import java.util.ArrayList;


public class FragmentList extends Fragment  implements StudentAdapter.OnItemClickListener {
    private String mName;
    private String mRollno;
    private String mClass;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private StudentAdapter studentAdapter;
    Context context;
    private Button btnAdd;
    private String mService;
    private ArrayList<Student> mArrayList=new ArrayList<>();
    Constants constants;
    Student student=new Student();
    int getPosition;
    private MyClickListener mMyClickListener;
    public FragmentList() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_fragment_list, container, false);
        context=getActivity().getApplicationContext();
        DataBaseHelper dataBaseHelper=new DataBaseHelper(context);
        recyclerView = view.findViewById(R.id.recycler_View);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        studentAdapter = new StudentAdapter(mArrayList);
        constants=new Constants();
        recyclerView.setAdapter(studentAdapter);
         btnAdd=(Button)view.findViewById(R.id.fragment_btn_add);
         mService=constants.BACKGROUND_TASK;
         studentAdapter.setOnItemClickListener(this);
         Cursor cursor=dataBaseHelper.getAllData();
         //fetching data from database;
        if(cursor!=null) {
            while (cursor.moveToNext()) {
                mArrayList.add(new Student(cursor.getString(0),
                        cursor.getString(1), cursor.getString(2)));

            }
        }
         btnAdd.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
              mMyClickListener.myClick(null);
             }
         });
        return view;
    }
    public void instantiateListener(MyClickListener myClickListener)
    {
        mMyClickListener=myClickListener;
    }

     public void insert(Bundle bundle) {
         if (bundle.getString(constants.ACTION_KEY).equals(constants.ADD)) {
             mArrayList.add(new Student(bundle.getString(constants.NAME_KEY), bundle.getString(constants.ROLLNO_KEY),
                     bundle.getString(constants.CLASS_KEY)));
             studentAdapter.notifyDataSetChanged();
         } else if (bundle.getString(constants.ACTION_KEY).equals(constants.EDIT)) {
             mArrayList.remove(getPosition);
             studentAdapter.notifyItemRemoved(getPosition);
             mArrayList.add(getPosition, new Student(bundle.getString(constants.NAME_KEY), bundle.getString(constants.ROLLNO_KEY),
                     bundle.getString(constants.CLASS_KEY)));
             studentAdapter.notifyDataSetChanged();
         }
     }

    @Override
    public void onItemClick(final int position, View v) {
        new AlertDialog.Builder(v.getContext())
                .setTitle("CHOOSE")
                .setPositiveButton(constants.DELETE, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Student student=mArrayList.get(position);
                        mName=student.getName();
                        mRollno=student.getRollno();
                        mClass=student.getClasses();
                        //deleting by async task
                         if(mService.equals(constants.BACKGROUND_TASK)) {
                             BackgroundTask backgroundTask = new BackgroundTask(getActivity());
                             backgroundTask.execute(constants.DELETE, mName, mRollno, mClass);
                         }
                         //deleting by service
                         else if(mService.equals(constants.BACKGROUND_SERVICES))
                         {
                             Intent intent=new Intent(getActivity(), BackgroundServices.class);
                             intent.putExtra(constants.NAME_KEY,mName);
                             intent.putExtra(constants.ROLLNO_KEY,mRollno);
                             intent.putExtra(constants.CLASS_KEY,mClass);
                             intent.putExtra(constants.ACTION_KEY,constants.DELETE);
                             getActivity().startService(intent);
                         }
                         //deleting by intent service
                         else if(mService.equals(constants.BACKGROUND_INTENTSERVICES))
                         {
                             Intent intent=new Intent(getActivity(), BackgroundIntentServices.class);
                             intent.putExtra(constants.NAME_KEY,mName);
                             intent.putExtra(constants.ROLLNO_KEY,mRollno);
                             intent.putExtra(constants.CLASS_KEY,mClass);
                             intent.putExtra(constants.ACTION_KEY,constants.DELETE);
                             getActivity().startService(intent);
                         }

                        studentAdapter.deleteItem(position);


                    }
                })
                .setNegativeButton(constants.EDIT, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //on clicking edit option
                         getPosition=position;
                         Student student=mArrayList.get(position);
                         mName=student.getName();
                         mRollno=student.getRollno();
                         mClass=student.getClasses();
                         Bundle bundle=new Bundle();
                         bundle.putString(constants.NAME_KEY,mName);
                         bundle.putString(constants.ROLLNO_KEY,mRollno);
                         bundle.putString(constants.CLASS_KEY,mClass);
                         bundle.putString(constants.ACTION_KEY,constants.EDIT);
                         mMyClickListener.myClick(bundle);


                    }
                })
                .setNeutralButton(constants.VIEW, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //on clicking view option
                        student=mArrayList.get(position);
                        mName=student.getName();
                        mRollno=student.getRollno();
                        mClass=student.getClasses();
                        Bundle bundle = new Bundle();
                        bundle.putString(constants.NAME_KEY,mName);
                        bundle.putString(constants.ROLLNO_KEY,mRollno);
                        bundle.putString(constants.CLASS_KEY,mClass);
                        bundle.putString(constants.ACTION_KEY,constants.VIEW);
                        Intent intent = new Intent(getActivity(), ViewActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }

                })

                .show();
    }


    public void getService(String service)
    {
      mService=service;
    }




    public interface MyClickListener
    {
        void myClick(Bundle bundle);
    }
}
