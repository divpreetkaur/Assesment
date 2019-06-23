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
import android.widget.Toast;

import com.example.assignment5.R;
import com.example.assignment5.activity.ViewActivity;
import com.example.assignment5.adapters.StudentAdapter;
import com.example.assignment5.callback.MyClickListener;
import com.example.assignment5.callback.OnDataSavedListener;
import com.example.assignment5.callback.OnItemClickListener;
import com.example.assignment5.database.DataBaseHelper;
import com.example.assignment5.model.Student;
import com.example.assignment5.services.BackgroundIntentServices;
import com.example.assignment5.services.BackgroundServices;
import com.example.assignment5.services.BackgroundTask;
import com.example.assignment5.utilities.Constants;

import java.util.ArrayList;


public class FragmentList extends Fragment  implements OnItemClickListener, OnDataSavedListener{
    private String mName;
    private String mRollno;
    private String mClass;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private StudentAdapter studentAdapter;
    private Context context;
    private Button btnAdd;
    private String mService;
    private ArrayList<Student> mArrayList=new ArrayList<>();
    private Constants constants;
    private Student student=new Student();
    private int clickPosition;
    private int deletePosition;
    private MyClickListener mMyClickListener;
    public FragmentList() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_fragment_list, container, false);
        context=getActivity().getApplicationContext();
        recyclerView = view.findViewById(R.id.recycler_View);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        constants=new Constants();
         btnAdd=(Button)view.findViewById(R.id.fragment_btn_add);
         mService=constants.BACKGROUND_TASK;
        studentAdapter = new StudentAdapter(mArrayList);
        recyclerView.setAdapter(studentAdapter);
        fetchAllData();
         btnAdd.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
              mMyClickListener.myClick(student,constants.ADD);
             }
         });
        return view;
    }
    public void fetchAllData()
    {     if(mService.equals(constants.BACKGROUND_TASK)) {
        new BackgroundTask(getActivity(), FragmentList.this, constants.READ_OPERATION).execute(student);
    }
    else if(mService.equals(constants.BACKGROUND_SERVICES))
    {   Intent intent=new Intent(getActivity(),BackgroundServices.class);
        getActivity().startService(intent.putExtra(constants.ACTION_KEY,constants.READ_OPERATION));
    }
    else if(mService.equals(constants.BACKGROUND_INTENTSERVICES))
    {   Intent intent=new Intent(getActivity(),BackgroundIntentServices.class);
         getActivity().startService(intent.putExtra(constants.ACTION_KEY,constants.READ_OPERATION));

    }
    }

    public void instantiateListener(MyClickListener myClickListener)
    {
        mMyClickListener=myClickListener;
    }
     public void insert(final String actionType, Student student) {
         if (actionType.equals(constants.ADD)) {
             mArrayList.add(student);
             studentAdapter.notifyDataSetChanged();
             showToast(constants.ADD);
         } else if (actionType.equals(constants.EDIT)) {
             mArrayList.remove(clickPosition);
             studentAdapter.notifyItemRemoved(clickPosition);
             mArrayList.add(clickPosition,student);
             studentAdapter.notifyDataSetChanged();
             showToast(constants.EDIT);


         }
     }
     public void showToast(String actionType)
     {
         if(actionType.equals(constants.ADD))
         {
             Toast.makeText(getActivity(),getResources().getString(R.string.data_entered),Toast.LENGTH_LONG).show();
         }
         else
             Toast.makeText(getActivity(),getResources().getString(R.string.data_updated),Toast.LENGTH_LONG).show();
     }

    @Override
    public void onItemClick(final int position, View v) {
        new AlertDialog.Builder(v.getContext())
                .setTitle(getResources().getString(R.string.choose))
                .setPositiveButton(getResources().getString(R.string.delete_option), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Student student=mArrayList.get(position);
                        mName=student.getName();
                        mRollno=student.getRollno();
                        mClass=student.getClasses();
                          deletePosition=position;
                        //deleting by async task
                         if(mService.equals(constants.BACKGROUND_TASK)) {
                             BackgroundTask backgroundTask =
                                     new BackgroundTask(getActivity(),FragmentList.this,constants.DELETE);

                             backgroundTask.execute(student);
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



                    }
                })
                .setNegativeButton(getResources().getString(R.string.edit_option), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //on clicking edit option
                         clickPosition=position;
                         Student student=mArrayList.get(position);
                         mMyClickListener.myClick(student,constants.EDIT);


                    }
                })
                .setNeutralButton(getResources().getString(R.string.view_option), new DialogInterface.OnClickListener() {
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

    public void deleteItem()
    {

        studentAdapter.deleteItem(deletePosition);
        Toast.makeText(getActivity(),getResources().getString(R.string.data_deleted),Toast.LENGTH_LONG).show();
    }
    public void setService(String service)
    {
      mService=service;

    }



    @Override
    public void onDataSavedSuccess(boolean isAddStudent, Student student) {

    }

    @Override
    public void onDataSavedError(boolean isAddStudent) {

    }

    @Override
    public void onDeleteSuccess() {
        Toast.makeText(getActivity(), getResources().getString(R.string.data_deleted), Toast.LENGTH_LONG).show();
        mMyClickListener.myClick(null,constants.DELETE);

    }

    @Override
    public void onDeleteError() {

    }
   //adding the fetched arraylist from database into recycler view
    @Override
    public void onFetchStudentList(ArrayList<Student> studentArrayList) {
        mArrayList=studentArrayList;
        studentAdapter=new StudentAdapter(studentArrayList);
        studentAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(studentAdapter);



    }



}
