package com.example.assignment5mvp.ui.home.detailfragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
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

import com.example.assignment5mvp.R;
import com.example.assignment5mvp.constant.Constants;
import com.example.assignment5mvp.data.db.DataBaseHandler;
import com.example.assignment5mvp.data.db.DatabaseUtil;
import com.example.assignment5mvp.data.model.Student;
import com.example.assignment5mvp.data.service.BackgroundIntentService;
import com.example.assignment5mvp.data.service.BackgroundService;
import com.example.assignment5mvp.data.service.BackgroundTask;
import com.example.assignment5mvp.ui.home.callback.DetailFragmentClickListener;
import com.example.assignment5mvp.ui.home.callback.OnDataSaveListener;

import java.io.Serializable;
import java.util.ArrayList;


public class DetailFragment extends Fragment implements DetailFragmentView, OnDataSaveListener, Serializable {
    private EditText etName,etRollno,etCls;
    private Button btnSubmit;
    private String mName,mRollno,mCls;
    private DetailFragmentClickListener detailFragmentClickListener;
    private DetailFragmentPresenter detailFragmentPresenter;
    private String mAction;
    private Constants constants;
    private static DataBaseHandler dataBaseHandler;
    private ArrayList<Student> mStudentArrayList;
    DataReceiver dataReceiver=new DataReceiver();
    public DetailFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    //registering broadcast receiver
    @Override
    public void onStart() {
        super.onStart();

        IntentFilter intentFilter = new IntentFilter(constants.BROADCAST_ACTION);
        LocalBroadcastManager.getInstance(getActivity().getApplicationContext()).registerReceiver(dataReceiver, intentFilter);
    }
    //unregister broadcast receiver
    @Override
    public void onStop() {

        super.onStop();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(dataReceiver);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_detail, container, false);
         init(view);

            Bundle bundle=getArguments();
        if (bundle != null && bundle.getString(constants.ACTION_KEY).equals(constants.VIEW)) {
            etName.setEnabled(false);
            etRollno.setEnabled(false);
            etCls.setEnabled(false);
            etName.setText(bundle.getString(constants.NAME_KEY));
            etRollno.setText(bundle.getString(constants.ROLLNO_KEY));
            etCls.setText(bundle.getString(constants.CLASS_KEY));
            btnSubmit.setVisibility(View.INVISIBLE);
        }
        //instantiating the detailfragmentpresenter
        detailFragmentPresenter=new DetailFragmentPresenterImpl(this,this,new DetailFragmentInteractorImpl());
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              mName=etName.getText().toString();
              mRollno=etRollno.getText().toString();
              mCls=etCls.getText().toString();
              detailFragmentPresenter.validateText(mName,mRollno,mCls,dataBaseHandler,mAction);

            }
        });
        return view;
    }
    //initialising variables
    public void init(View view)
    {
        dataBaseHandler=DatabaseUtil.getDataBase(getActivity());
        mStudentArrayList=new ArrayList<>();
        etName=view.findViewById(R.id.detail_et_name);
        etRollno=view.findViewById(R.id.detail_et_rollno);
        etCls=view.findViewById(R.id.detail_et_class);
        btnSubmit=view.findViewById(R.id.detail_btn);
        constants=new Constants();
        mAction=constants.ADD;
    }
    public static DetailFragment newInstance(Bundle bundle) {
        DetailFragment detailFragment = new DetailFragment();


        if (bundle != null)
            detailFragment.setArguments(bundle);
             return detailFragment;


    }

  public void instantiateListener(DetailFragmentClickListener detailFragmentClickListener)
  {
      this.detailFragmentClickListener=detailFragmentClickListener;
  }

    @Override
    public void onSuccess() {
        new AlertDialog.Builder(getContext())
                .setTitle(getResources().getString(R.string.choose))
                .setPositiveButton(getResources().getString(R.string.background_task), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //to perform database operations using async task
                        detailFragmentClickListener.setService(constants.BACKGROUND_TASK);
                       Student student = new Student(mName, mRollno, mCls);
                       detailFragmentPresenter.performDbOperation(dataBaseHandler,constants.BACKGROUND_TASK,student,mAction);




                    }
                })
                .setNegativeButton(getResources().getString(R.string.background_service), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //to perform database operations using service
                        detailFragmentClickListener.setService(constants.BACKGROUND_SERVICES);
                        startService();





                    }
                })
                .setNeutralButton(getResources().getString(R.string.background_intentservice), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //to perform database operations using intent service

                        detailFragmentClickListener.setService(constants.BACKGROUND_INTENTSERVICES);
                         startIntentService();


                    }
                })

                .show();

    }
    public void startService()
    {
        Intent intent = new Intent(getActivity(), BackgroundService.class);
        intent.putExtra(constants.NAME_KEY, etName.getText().toString());
        intent.putExtra(constants.ROLLNO_KEY, etRollno.getText().toString());
        intent.putExtra(constants.CLASS_KEY, etCls.getText().toString());
        intent.putExtra(constants.ACTION_KEY, mAction);
        getActivity().startService(intent);
    }
   public void startIntentService()
   {
       Intent intent = new Intent(getActivity(), BackgroundIntentService.class);
       intent.putExtra(constants.NAME_KEY, etName.getText().toString());
       intent.putExtra(constants.ROLLNO_KEY, etRollno.getText().toString());
       intent.putExtra(constants.CLASS_KEY, etCls.getText().toString());
       intent.putExtra(constants.ACTION_KEY, mAction);
       getActivity().startService(intent);
   }
    public void clearEditText() {
        etName.getText().clear();
        etRollno.getText().clear();
        etRollno.setEnabled(true);
        etCls.getText().clear();
        setBtnAction(constants.ADD);
    }
    public void setEditText(Student student) {
        etName.setText(student.getName());
        etRollno.setText(student.getRollno());
        etRollno.setEnabled(false);
        etCls.setText(student.getClasses());
        setBtnAction(constants.EDIT);
    }
    public void setBtnAction(String actionType)
    {
        mAction=actionType;
    }
    //showing error message on failure of validation
    @Override
    public void onFailure(int errorMessage) {
        Toast.makeText(getActivity(),errorMessage,Toast.LENGTH_SHORT).show();

    }
    //performing further operations if data is successfully saved in database
    @Override
    public void onDataSavedSuccess(boolean isAddStudent, Student student) {
        detailFragmentClickListener.myClick(mAction,student);
    }
    //showing error message if data is not saved in database
    @Override
    public void onDataSavedError(boolean isAddStudent) {
        if(isAddStudent)
        {
            Toast.makeText(getActivity(),getResources().getString(R.string.add_data_fail),Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(getActivity(),getResources().getString(R.string.update_data_fail),Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    public void onDeleteSuccess() {

    }

    @Override
    public void onDeleteError() {

    }

    @Override
    public void onFetchStudentList(ArrayList<Student> studentArrayList) {

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
                    Student student=new Student(mName,mRollno,mCls);
                    detailFragmentClickListener.myClick(constants.ADD,student);


                } else if (intent.getStringExtra(constants.ACTION_KEY).equals(constants.EDIT)) {
                    Student student=new Student(mName,mRollno,mCls);
                    detailFragmentClickListener.myClick(constants.EDIT,student);

                } else if (intent.getStringExtra(constants.ACTION_KEY).equals(constants.DELETE)) {
                     detailFragmentClickListener.myClick(constants.DELETE,null);


                } else if (intent.getStringExtra(constants.ACTION_KEY).equals(constants.READ_OPERATION)) {

                    mStudentArrayList = intent.getParcelableArrayListExtra(constants.ARRAY_LIST);
                    detailFragmentClickListener.fetchDBList(mStudentArrayList);


                }
                //if database operation is not performed successfully,checking the
                //action key and calling function in activity class to show a toast
            } else if (intent.getStringExtra(constants.IS_SUCCESS).equals(constants.FALSE)) {
                if (intent.getStringExtra(constants.ACTION_KEY).equals(constants.ADD)) {
                     detailFragmentClickListener.onDBoperationError(constants.ADD);
                } else if (intent.getStringExtra(constants.ACTION_KEY).equals(constants.EDIT)) {
                    detailFragmentClickListener.onDBoperationError(constants.EDIT);
                } else if (intent.getStringExtra(constants.ACTION_KEY).equals(constants.DELETE)) {
                    detailFragmentClickListener.onDBoperationError(constants.DELETE);
                }
            }
        }
    }
}
