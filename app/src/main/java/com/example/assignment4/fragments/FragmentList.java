package com.example.assignment4.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.assignment4.R;
import com.example.assignment4.activity.ViewActivity;
import com.example.assignment4.adapters.StudentAdapter;
import com.example.assignment4.models.Student;
import com.example.assignment4.utilities.constants;

import java.util.ArrayList;


public class FragmentList extends Fragment implements StudentAdapter.OnItemClickListener {

  private MyClickListener mMyClickListener;
  private String mName;
  private String mRollno;
  private String mClass;
  RecyclerView recyclerView;
  RecyclerView.LayoutManager layoutManager;
  String action;
  StudentAdapter studentAdapter;
  TextView textView;
  int getPosition;
  Student student=new Student();
  constants constants=new constants();
  private ArrayList<Student> mArrayList=new ArrayList<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_fragment_list, container, false);

       final   Button button=(Button)view.findViewById(R.id.fragment_btn_add);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_View);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        studentAdapter = new StudentAdapter(mArrayList);
        recyclerView.setAdapter(studentAdapter);
        textView=(TextView)view.findViewById(R.id.list_tv);
        if(mArrayList.size()==0)
        {
            textView.setVisibility(View.VISIBLE);
        }
        studentAdapter.setOnItemClickListener(this);
       recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               mMyClickListener.myClick(getString(R.string.add),null);
               getBtnAction(getString(R.string.add));


            }
        });
        return view;
            }
            void getBtnAction(String action)
            {
                this.action=action;
            }

            public void getData(String name,String rollno,String cls)
            {



                if(action.equals(getString(R.string.edit))) {
                    mArrayList.remove(getPosition);
                    studentAdapter.notifyItemRemoved(getPosition);
                    mArrayList.add(getPosition, new Student(name, rollno, cls));
                    studentAdapter.notifyDataSetChanged();
                }
                else
                {
                    mArrayList.add(new Student(name,rollno,cls));
                    studentAdapter.notifyDataSetChanged();
                }
                if(mArrayList.size()!=0)
                    textView.setVisibility(View.INVISIBLE);



                }
                public void instantiateListener(MyClickListener clickListener) {
                    mMyClickListener = clickListener;
    }

    @Override
    public void onItemClick(final int position, View v) {
        new AlertDialog.Builder(v.getContext())
                .setTitle(getString(R.string.option))
                .setPositiveButton(getString(R.string.delete), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //on clicking delete function
                        studentAdapter.deleteItem(position);

                    }
                })
                .setNegativeButton(getString(R.string.edit), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //on clicking edit option
                        student=mArrayList.get(position);
                        getPosition=position;
                        mName=student.getName();
                        mRollno=student.getRollno();
                        mClass=student.getClasses();
                        Bundle bundle = new Bundle();
                        bundle.putString(constants.NAME_KEY,mName);
                        bundle.putString(constants.ROLLNO_KEY,mRollno);
                        bundle.putString(constants.CLASS_KEY,mClass);
                        getBtnAction(getString(R.string.edit));
                        mMyClickListener.myClick(getString(R.string.edit),bundle);

                    }
                })
                .setNeutralButton(getString(R.string.show), new DialogInterface.OnClickListener() {
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
                        Intent intent = new Intent(getActivity(), ViewActivity.class);
                       intent.putExtras(bundle);
                        startActivity(intent);
                    }
                })

                .show();
    }


    public interface MyClickListener
    {
        void myClick(String action,Bundle bundle);
    }

    }



