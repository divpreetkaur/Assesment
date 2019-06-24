package com.example.assignment5.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.assignment5.R;
import com.example.assignment5.activity.HomeActivity;
import com.example.assignment5.callback.ClickListener;
import com.example.assignment5.callback.OnDataSavedListener;
import com.example.assignment5.database.DataBaseHelper;
import com.example.assignment5.model.Student;
import com.example.assignment5.services.BackgroundIntentServices;
import com.example.assignment5.services.BackgroundServices;
import com.example.assignment5.services.BackgroundTask;
import com.example.assignment5.utilities.Constants;
import com.example.assignment5.utilities.Helper;

import java.util.ArrayList;


public class FragmentDetail extends Fragment implements OnDataSavedListener {
    private Button btnSubmit;
    private EditText etName, etRollno, etCls;
    private Constants constants = new Constants();
    private String mAction;
    private String mName, mRollno, mCls;
    private Helper helper;
    private Context context;
    private ClickListener clickListener;
    private String mOldRollNumber;
    private Student student;
    private DataReceiver dataReceiver = new DataReceiver();
    private ArrayList<Student> mStudentArrayList;


    public FragmentDetail() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        helper = new Helper();
        context = getActivity();
        View view = inflater.inflate(R.layout.fragment_fragment_detail, container, false);
        init(view);

        Bundle bundle = getArguments();
        if (bundle != null && bundle.getString(constants.ACTION_KEY).equals(constants.VIEW)) {
            etName.setEnabled(false);
            etRollno.setEnabled(false);
            etCls.setEnabled(false);
            etName.setText(bundle.getString(constants.NAME_KEY));
            etRollno.setText(bundle.getString(constants.ROLLNO_KEY));
            etCls.setText(bundle.getString(constants.CLASS_KEY));
            btnSubmit.setVisibility(View.INVISIBLE);
        }


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mName = etName.getText().toString();
                mRollno = etRollno.getText().toString();
                mCls = etCls.getText().toString();
                if (helper.validate(context, mName, mRollno, mCls)) {
                    BackgroundTask backgroundTask = new BackgroundTask(getActivity(), FragmentDetail.this, constants.READ_OPERATION);
                    backgroundTask.execute(student);

                }

            }

        });


        return view;
    }

    //registering broadcast receiver
    @Override
    public void onStart() {
        super.onStart();

        IntentFilter intentFilter = new IntentFilter(constants.BROADCAST_ACTION);
        LocalBroadcastManager.getInstance(getActivity().getApplicationContext()).registerReceiver(dataReceiver, intentFilter);
    }


    public static FragmentDetail newInstance(Bundle bundle) {
        FragmentDetail fragmentDetail = new FragmentDetail();


        if (bundle != null)

            fragmentDetail.setArguments(bundle);
        return fragmentDetail;

    }

    public void clearEditText() {
        etName.getText().clear();
        etRollno.getText().clear();
        etCls.getText().clear();
        setBtnAction(constants.ADD);
    }


    void init(View view) {
        mAction = constants.ADD;
        mStudentArrayList = new ArrayList<>();
        mOldRollNumber = constants.OLDROLLNUMBER;
        etName = (EditText) view.findViewById(R.id.detail_et_name);
        etRollno = (EditText) view.findViewById(R.id.detail_et_rollno);
        etCls = (EditText) view.findViewById(R.id.detail_et_class);
        btnSubmit = (Button) view.findViewById(R.id.detail_btn);
    }

    public void instantiateListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }
    public void setBtnAction(String action) {
        this.mAction = action;
    }

    public void onItemClick() {
        new AlertDialog.Builder(getContext())
                .setTitle(getResources().getString(R.string.choose))
                .setPositiveButton(getResources().getString(R.string.background_task), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //to perform database operations using async task
                        clickListener.setService(constants.BACKGROUND_TASK);
                        mName = etName.getText().toString();
                        mRollno = etRollno.getText().toString();
                        mCls = etCls.getText().toString();
                        Student student = new Student(mName, mRollno, mCls, mOldRollNumber);
                        BackgroundTask backgroundTask = new BackgroundTask(getActivity(), FragmentDetail.this, mAction);
                        backgroundTask.execute(student);



                    }
                })
                .setNegativeButton(getResources().getString(R.string.background_service), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //to perform database operations using service

                        clickListener.setService(constants.BACKGROUND_SERVICES);
                        Intent intent = new Intent(getActivity(), BackgroundServices.class);
                        mName = etName.getText().toString();
                        mRollno = etRollno.getText().toString();
                        mCls = etCls.getText().toString();
                        intent.putExtra(constants.NAME_KEY, etName.getText().toString());
                        intent.putExtra(constants.ROLLNO_KEY, etRollno.getText().toString());
                        intent.putExtra(constants.CLASS_KEY, etCls.getText().toString());
                        intent.putExtra(constants.OLDROLLNUMBER, mOldRollNumber);
                        intent.putExtra(constants.ACTION_KEY, mAction);
                        getActivity().startService(intent);



                    }
                })
                .setNeutralButton(getResources().getString(R.string.backgound_intentService), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //to perform database operations using intent service
                        clickListener.setService(constants.BACKGROUND_INTENTSERVICES);
                        mName = etName.getText().toString();
                        mRollno = etRollno.getText().toString();
                        mCls = etCls.getText().toString();
                        Intent intent = new Intent(getActivity(), BackgroundIntentServices.class);
                        intent.putExtra(constants.NAME_KEY, etName.getText().toString());
                        intent.putExtra(constants.ROLLNO_KEY, etRollno.getText().toString());
                        intent.putExtra(constants.CLASS_KEY, etCls.getText().toString());
                        intent.putExtra(constants.OLDROLLNUMBER, mOldRollNumber);
                        intent.putExtra(constants.ACTION_KEY, mAction);
                        getActivity().startService(intent);



                    }
                })

                .show();
    }

    public void setEditText(Student student) {
        etName.setText(student.getName());
        mOldRollNumber = student.getRollno();
        etRollno.setText(student.getRollno());
        etCls.setText(student.getClasses());
        setBtnAction(constants.EDIT);
    }
    //when data is successfully added or updated
    @Override
    public void onDataSavedSuccess(boolean isAddStudent, final Student student) {

        if (isAddStudent) {
            clickListener.onClick(student, constants.ADD);

        } else {
            clickListener.onClick(student, constants.EDIT);

        }
    }
    //when data not updated or added
    @Override
    public void onDataSavedError(boolean isAddStudent) {
        if (isAddStudent) {
            Toast.makeText(getActivity(), getResources().getString(R.string.data_not_added), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getActivity(), getResources().getString(R.string.data_not_updated), Toast.LENGTH_LONG).show();
        }
    }
    //on successfull deletion of data
    @Override
    public void onDeleteSuccess() {
        clickListener.onClick(null, constants.DELETE);

    }
    //if data is not deleted
    @Override
    public void onDeleteError() {

    }
    //fetching the student arraylist from database
    @Override
    public void onFetchStudentList(ArrayList<Student> studentArrayList) {
        int flag = 0;
        String newRollno=etRollno.getText().toString();
         ArrayList<String> rollnoArrayList=new ArrayList<>();
        for (int i = 0; i < studentArrayList.size(); i++) {
            Student student = studentArrayList.get(i);
                rollnoArrayList.add(student.getRollno());
        }
        if (rollnoArrayList.contains(newRollno) && !newRollno.equals(mOldRollNumber)) {
            Toast.makeText(getActivity(),getResources().getString(R.string.rollno_validate), Toast.LENGTH_LONG).show();

        } else {
            onItemClick();
        }

    }
    //broadcast receiver
    public class DataReceiver extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent) {
            //if database operation is performed successfully
            //checking the action key and performing the further operations
            if (intent.getStringExtra(constants.IS_SUCCESS).equals(constants.TRUE)) {
                if (intent.getStringExtra(constants.ACTION_KEY).equals(constants.ADD)) {
                    Student student=new Student(mName,mRollno,mCls,constants.OLD_ROLL_NO);
                    clickListener.onClick(student,constants.ADD);

                } else if (intent.getStringExtra(constants.ACTION_KEY).equals(constants.EDIT)) {
                    Student student=new Student(mName,mRollno,mCls,mOldRollNumber);
                     clickListener.onClick(student,constants.EDIT);
                } else if (intent.getStringExtra(constants.ACTION_KEY).equals(constants.DELETE)) {
                    clickListener.onClick(null,constants.DELETE);

                } else if (intent.getStringExtra(constants.ACTION_KEY).equals(constants.READ_OPERATION)) {

                    mStudentArrayList = intent.getParcelableArrayListExtra(constants.ARRAY_LIST);
                    clickListener.fetchDbList(mStudentArrayList, constants.ACTION_KEY);
                }
                //if database operation is not performed successfully,checking the
                //action key and calling function in activity class to show a toast
            } else if (intent.getStringExtra(constants.IS_SUCCESS).equals(constants.FALSE)) {
                if (intent.getStringExtra(constants.ACTION_KEY).equals(constants.ADD)) {
                    clickListener.onDbOperationError(constants.ADD);
                } else if (intent.getStringExtra(constants.ACTION_KEY).equals(constants.EDIT)) {
                    clickListener.onDbOperationError(constants.EDIT);
                } else if (intent.getStringExtra(constants.ACTION_KEY).equals(constants.DELETE)) {
                    clickListener.onDbOperationError(constants.DELETE);
                }
            }
        }
    }
}

