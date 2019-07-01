package com.example.assignment5mvp.ui.home.listfragment;

import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.Toast;

import com.example.assignment5mvp.R;
import com.example.assignment5mvp.constant.Constants;
import com.example.assignment5mvp.data.db.DataBaseHandler;
import com.example.assignment5mvp.data.db.DatabaseUtil;
import com.example.assignment5mvp.data.model.Student;
import com.example.assignment5mvp.data.service.BackgroundIntentService;
import com.example.assignment5mvp.data.service.BackgroundService;
import com.example.assignment5mvp.data.service.BackgroundTask;
import com.example.assignment5mvp.ui.home.ViewActivity;
import com.example.assignment5mvp.ui.home.callback.ListFragmentClickListener;
import com.example.assignment5mvp.ui.home.callback.OnDataSaveListener;
import com.example.assignment5mvp.ui.home.callback.OnItemClickListener;

import java.util.ArrayList;


public class ListFragment extends Fragment implements ListFragmentView, OnItemClickListener, OnDataSaveListener {
    private ListFragmentClickListener listFragmentClickListener;
    private Button btnAdd;
    private String mName;
    private String mRollno;
    private String mClass;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private StudentAdapter studentAdapter;
    private String mService;
    private ArrayList<Student> mArrayList;
    private Constants constants;
    private Student student=new Student();
    private int clickPosition;
    private int deletePosition;
    private DataBaseHandler dataBaseHandler;
    private ListFragmentPresenter listFragmentPresenter;


    public ListFragment()
    {

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_list, container, false);
        recyclerView = view.findViewById(R.id.recycler_View);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        constants=new Constants();
        mArrayList=new ArrayList<>();
        mService=constants.BACKGROUND_TASK;
        studentAdapter = new StudentAdapter(mArrayList);
        recyclerView.setAdapter(studentAdapter);
        // Inflate the layout for this fragment

        btnAdd=view.findViewById(R.id.fragment_btn_add);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               listFragmentClickListener.onClick(constants.ADD,student);
            }
        });
        listFragmentPresenter=new ListFragmentPresenterImpl(this,new ListFragmentInteractorImpl(),this);
        //fetching database object from util class
        dataBaseHandler= DatabaseUtil.getDataBase(getActivity());

           fetchAllDataFromDb();


        return view;
    }
    public void instantiateListener(ListFragmentClickListener listFragmentClickListener)
    {
        this.listFragmentClickListener=listFragmentClickListener;
    }
   public void fetchAllDataFromDb()
   {
       if(mService.equals(constants.BACKGROUND_TASK))
       {
           listFragmentPresenter.performDbOperation(student,constants.READ_OPERATION,dataBaseHandler);
       }
       else if(mService.equals(constants.BACKGROUND_SERVICES))
       {   Intent intent=new Intent(getActivity(),BackgroundService.class);
           getActivity().startService(intent.putExtra(constants.ACTION_KEY,constants.READ_OPERATION));

       }
       else if(mService.equals(constants.BACKGROUND_INTENTSERVICES))
       {   Intent intent=new Intent(getActivity(),BackgroundIntentService.class);
           getActivity().startService(intent.putExtra(constants.ACTION_KEY,constants.READ_OPERATION));

       }
   }
    @Override
    public void onSuccess() {

    }

    @Override
    public void onFailure(int errorMessage) {
        Toast.makeText(getActivity(),errorMessage,Toast.LENGTH_SHORT).show();

    }
    //inserting data from database into recycler view
    public void insertData(String actionType,Student student)
    {
        if(actionType.equals(constants.ADD))
        {
            mArrayList.add(student);
            studentAdapter.notifyDataSetChanged();
        }
        else if(actionType.equals(constants.EDIT))
        {
            mArrayList.remove(clickPosition);
            studentAdapter.notifyItemRemoved(clickPosition);
            mArrayList.add(clickPosition,student);
            studentAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemClick(final int position,View view) {
        new AlertDialog.Builder(view.getContext())
                .setTitle(getResources().getString(R.string.choose))
                .setPositiveButton(getResources().getString(R.string.delete_option), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                       Student student=mArrayList.get(position);
                        mName=student.getName();
                        mRollno=student.getRollno();
                        mClass=student.getClasses();
                        deletePosition=position;
//                        //deleting by async task
                       if(mService.equals(constants.BACKGROUND_TASK)) {
                            listFragmentPresenter.performDbOperation(student,constants.DELETE,dataBaseHandler);

                       }

//                        //deleting by service
                        else if(mService.equals(constants.BACKGROUND_SERVICES))
                        {
                           startService();

                        }
//                        //deleting by intent service
                       else if(mService.equals(constants.BACKGROUND_INTENTSERVICES))
                        {
                            startIntentService();
//
                        }



                    }
                })
                .setNegativeButton(getResources().getString(R.string.edit_option), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {



                      //on clicking edit option
                        clickPosition=position;
                        Student student=mArrayList.get(position);
                        listFragmentClickListener.onClick(constants.EDIT,student);


                    }
                })
                .setNeutralButton(getResources().getString(R.string.view_option), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //on clicking view option
                        student=mArrayList.get(position);
                        setView(student);

                    }

                })

                .show();
    }
    public void setView(Student student)
    {
        Bundle bundle=new Bundle();
        bundle.putString(constants.NAME_KEY,student.getName());
        bundle.putString(constants.ROLLNO_KEY,student.getRollno());
        bundle.putString(constants.CLASS_KEY,student.getClasses());
        bundle.putString(constants.ACTION_KEY,constants.VIEW);
        Intent intent=new Intent(getActivity(), ViewActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    //deletion by service
    public void startService()
    { Intent intent = new Intent(getActivity(), BackgroundService.class);
       intent.putExtra(constants.NAME_KEY,mName);
        intent.putExtra(constants.ROLLNO_KEY,mRollno);
        intent.putExtra(constants.CLASS_KEY,mClass);
        intent.putExtra(constants.ACTION_KEY,constants.DELETE);
        getActivity().startService(intent);
    }
    //deletion by intent service
    public void startIntentService()
    {
        Intent intent = new Intent(getActivity(), BackgroundIntentService.class);
        intent.putExtra(constants.NAME_KEY,mName);
        intent.putExtra(constants.ROLLNO_KEY,mRollno);
        intent.putExtra(constants.CLASS_KEY,mClass);
        intent.putExtra(constants.ACTION_KEY,constants.DELETE);
        getActivity().startService(intent);
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
           studentAdapter.deleteItem(deletePosition);
    }

    @Override
    public void onDeleteError() {
        Toast.makeText(getActivity(),getResources().getString(R.string.delete_data_fail),Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onFetchStudentList(ArrayList<Student> studentArrayList) {
        mArrayList=new ArrayList<>();
        mArrayList=studentArrayList;
        studentAdapter=new StudentAdapter(studentArrayList);
        recyclerView.setAdapter(studentAdapter);
        studentAdapter.setOnItemClickListener(this);

    }
    public void deleteData()
    {
        studentAdapter.deleteItem(deletePosition);
    }
}





