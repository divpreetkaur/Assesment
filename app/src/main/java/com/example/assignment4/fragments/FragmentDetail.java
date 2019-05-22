package com.example.assignment4.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.assignment4.R;
import com.example.assignment4.utilities.constants;
import com.example.assignment4.utilities.helper;


public class FragmentDetail extends Fragment {
   ClickListener clickListener;
   Button btnSubmit;
   EditText etName;
   EditText etRollno;
   EditText etClass;
  private String mName;
  private String mRollno;
   private String mCls;
  constants constants=new constants();
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
        View view = inflater.inflate(R.layout.fragment_fragment_detail, container, false);
        init(view);
        final Context context = getActivity();

        Bundle bundle = getArguments();
        if (bundle != null) {
            etName.setText(bundle.getString(constants.NAME_KEY));
            etRollno.setText(bundle.getString(constants.ROLLNO_KEY));
            etClass.setText(bundle.getString(constants.CLASS_KEY));
            etClass.setEnabled(false);
            etRollno.setEnabled(false);
            etName.setEnabled(false);
            btnSubmit.setVisibility(View.INVISIBLE);
        }
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mName = etName.getText().toString();
                mRollno = etRollno.getText().toString();
                mCls = etClass.getText().toString();
                if (helper.validate(context, mName, mRollno,mCls)) {
                    Bundle bundle = new Bundle();
                    bundle.putString(constants.NAME_KEY, mName);
                    bundle.putString(constants.ROLLNO_KEY, mRollno);
                    bundle.putString(constants.CLASS_KEY, mCls);
                    clickListener.onClick(mName, mRollno, mCls);
                }
            }
        });
        return view;
    }
        public void clearEditText()
        {
            etName.getText().clear();
            etRollno.getText().clear();
            etClass.getText().clear();
        }
  //initialising the views
    void init(View view)
    {
        helper helper=new helper();
     btnSubmit=(Button)view.findViewById(R.id.detail_btn);
     etName=(EditText)view.findViewById(R.id.detail_et_name);
     etRollno=(EditText)view.findViewById(R.id.detail_et_rollno);
     etClass=(EditText)view.findViewById(R.id.detail_et_class);
    }
   public void instantiate(ClickListener clickListener)
    {
        this.clickListener=clickListener;
    }
    public void setEditText(Bundle bundle) {
        if (bundle != null) {
            etName.setText(bundle.getString(constants.NAME_KEY));
            etRollno.setText(bundle.getString(constants.ROLLNO_KEY));
            etClass.setText(bundle.getString(constants.CLASS_KEY));

        }
    }


 public static FragmentDetail newInstance(Bundle bundle)
 {
     FragmentDetail fragmentDetail =new FragmentDetail();


     if(bundle!=null)

         fragmentDetail.setArguments(bundle);
         return fragmentDetail;

 }
  public interface ClickListener
   {
       void onClick(String name,String rollno,String cls);
   }

}
