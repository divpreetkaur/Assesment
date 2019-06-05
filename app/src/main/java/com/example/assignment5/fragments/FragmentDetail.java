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
import com.example.assignment5.database.DataBaseHelper;
import com.example.assignment5.services.BackgroundIntentServices;
import com.example.assignment5.services.BackgroundServices;
import com.example.assignment5.services.BackgroundTask;
import com.example.assignment5.utilities.Constants;
import com.example.assignment5.utilities.Helper;

import java.util.ArrayList;


public class FragmentDetail extends Fragment {
    private Button btnSubmit;
    private EditText etName, etRollno, etCls;
    private Constants constants = new Constants();
    private String action;
    private String name, rollno, cls;
    private Helper helper;
   private Context context;
    private DataBaseHelper dataBaseHelper;
    private ClickListener clickListener;
    String oldRollNumber;
    DataReceiver dataReceiver=new DataReceiver();

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
        dataBaseHelper = new DataBaseHelper(getActivity());
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
                name = etName.getText().toString();
                rollno = etRollno.getText().toString();
                cls = etCls.getText().toString();
                if (helper.validate(context, name, rollno, cls)) {
                    if (validateRollno()) {
                        onItemClick();
                    }

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
        LocalBroadcastManager.getInstance(getActivity().getApplicationContext()).registerReceiver(dataReceiver,intentFilter);
    }


    public static FragmentDetail newInstance(Bundle bundle) {
        FragmentDetail fragmentDetail = new FragmentDetail();


        if (bundle != null)

            fragmentDetail.setArguments(bundle);
        return fragmentDetail;

    }
    public void clearEditText()
    {
        etName.getText().clear();
        etRollno.getText().clear();
        etCls.getText().clear();
    }
    public boolean validateRollno() {
        int flag = 0;
        Cursor cursor = dataBaseHelper.getAllData();
        if(action.equals(constants.ADD))
            oldRollNumber="0";
         if(cursor!=null && !oldRollNumber.equals(etRollno.getText().toString())) {
             while (cursor.moveToNext()) {
                 if (etRollno.getText().toString().equals(cursor.getString(1))) {
                     Toast.makeText(getActivity(),constants.ROLLNO_VALIDATE, Toast.LENGTH_LONG).show();
                     etRollno.getText().clear();
                     flag = 1;
                     break;

                 }
             }
         }
        if (flag == 1)
            return false;
        else
            return true;
    }

    void init(View view) {
        action=constants.ADD;
        etName = (EditText) view.findViewById(R.id.detail_et_name);
        etRollno = (EditText) view.findViewById(R.id.detail_et_rollno);
        etCls = (EditText) view.findViewById(R.id.detail_et_class);
        btnSubmit = (Button) view.findViewById(R.id.detail_btn);
    }

    public void instantiateListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void getBtnAction(String action) {
        this.action = action;
    }

    public void onItemClick() {
        new AlertDialog.Builder(getContext())
                .setTitle(constants.DIALOGUE_TITLE)
                .setPositiveButton(constants.BACKGROUND_TASK, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //to perform database operations using async task
                        clickListener.getService(constants.BACKGROUND_TASK);
                        name = etName.getText().toString();
                        rollno = etRollno.getText().toString();
                        cls = etCls.getText().toString();

                        BackgroundTask backgroundTask = new BackgroundTask(getActivity());
                        backgroundTask.execute(action, name, rollno, cls,oldRollNumber);
                        Bundle bundle = new Bundle();
                        bundle.putString(constants.NAME_KEY, name);
                        bundle.putString(constants.ROLLNO_KEY, rollno);
                        bundle.putString(constants.CLASS_KEY, cls);
                        bundle.putString(constants.ACTION_KEY, action);
                        clickListener.onClick(bundle);
                        getBtnAction(constants.ADD);


                    }
                })
                .setNegativeButton(constants.BACKGROUND_SERVICES, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //to perform database operations using service

                        clickListener.getService(constants.BACKGROUND_SERVICES);
                        Intent intent = new Intent(getActivity(), BackgroundServices.class);
                        intent.putExtra(constants.NAME_KEY, etName.getText().toString());
                        intent.putExtra(constants.ROLLNO_KEY, etRollno.getText().toString());
                        intent.putExtra(constants.CLASS_KEY, etCls.getText().toString());
                        intent.putExtra(constants.OLDROLLNUMBER,oldRollNumber);
                        intent.putExtra(constants.ACTION_KEY, action);
                        getActivity().startService(intent);
                        name = etName.getText().toString();
                        rollno = etRollno.getText().toString();
                        cls = etCls.getText().toString();
                        Bundle bundle = new Bundle();
                        bundle.putString(constants.NAME_KEY, name);
                        bundle.putString(constants.ROLLNO_KEY, rollno);
                        bundle.putString(constants.CLASS_KEY, cls);
                        bundle.putString(constants.ACTION_KEY, action);
                        clickListener.onClick(bundle);
                        getBtnAction(constants.ADD);




                    }
                })
                .setNeutralButton(constants.BACKGROUND_INTENTSERVICES, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //to perform database operations using intent service
                        clickListener.getService(constants.BACKGROUND_INTENTSERVICES);
                        Intent intent = new Intent(getActivity(), BackgroundIntentServices.class);
                        intent.putExtra(constants.NAME_KEY, etName.getText().toString());
                        intent.putExtra(constants.ROLLNO_KEY, etRollno.getText().toString());
                        intent.putExtra(constants.CLASS_KEY, etCls.getText().toString());
                        intent.putExtra(constants.OLDROLLNUMBER,oldRollNumber);
                        intent.putExtra(constants.ACTION_KEY, action);
                        getActivity().startService(intent);
                        name = etName.getText().toString();
                        rollno = etRollno.getText().toString();
                        cls = etCls.getText().toString();
                        Bundle bundle = new Bundle();
                        bundle.putString(constants.NAME_KEY, name);
                        bundle.putString(constants.ROLLNO_KEY, rollno);
                        bundle.putString(constants.CLASS_KEY, cls);
                        bundle.putString(constants.ACTION_KEY, action);
                        clickListener.onClick(bundle);
                        getBtnAction(constants.ADD);


                    }
                })

                .show();
    }

    public void setEditText(Bundle bundle) {
        etName.setText(bundle.getString(constants.NAME_KEY));
        oldRollNumber=bundle.getString(constants.ROLLNO_KEY);
        etRollno.setText(bundle.getString(constants.ROLLNO_KEY));
        etCls.setText(bundle.getString(constants.CLASS_KEY));
        getBtnAction(constants.EDIT);
    }

    public interface ClickListener {
        void onClick(Bundle bundle);

        void getService(String service);
    }

    public class DataReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, constants.ENTERED_DATA, Toast.LENGTH_SHORT).show();

        }
    }
}