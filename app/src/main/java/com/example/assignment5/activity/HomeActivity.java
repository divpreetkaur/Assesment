package com.example.assignment5.activity;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.assignment5.R;
import com.example.assignment5.adapters.ViewPagerAdapter;
import com.example.assignment5.callback.ClickListener;
import com.example.assignment5.callback.MyClickListener;
import com.example.assignment5.callback.OnDataSavedListener;
import com.example.assignment5.database.DataBaseHelper;
import com.example.assignment5.fragments.FragmentDetail;
import com.example.assignment5.fragments.FragmentList;
import com.example.assignment5.model.Student;
import com.example.assignment5.services.BackgroundTask;
import com.example.assignment5.utilities.Constants;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements MyClickListener, ClickListener
        {

    private ViewPagerAdapter mViewPagerAdapter;
    private List<Fragment> mFragmentsList=new ArrayList<>();
    private List<String> mFragmentName = new ArrayList<String>();
    private Constants constants;
    private ViewPager mViewPager;
    private int mTabPosition;
    private TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        constants = new Constants();
        mViewPager = (ViewPager) findViewById(R.id.home_vp);
        tabLayout = (TabLayout) findViewById(R.id.tab);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //getting position of tab
                mTabPosition = tab.getPosition();
                if (mTabPosition == 1) {
                    FragmentDetail fragmentDetail = (FragmentDetail) mFragmentsList.get(1);
                    fragmentDetail.clearEditText();


                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        addPagesTofragmentList();
        getTitleList();
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), mFragmentsList, mFragmentName);
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.setCurrentItem(0);
        tabLayout.setupWithViewPager(mViewPager);

    }

   //adding fragments to arraylist
    private void addPagesTofragmentList() {

        mFragmentsList = new ArrayList<>();
        Fragment fragment = new FragmentList();
       ((FragmentList) fragment).instantiateListener(this);
        mFragmentsList.add(fragment);
        Fragment fragment1 = new FragmentDetail();
        ((FragmentDetail) fragment1).instantiateListener(this);
        mFragmentsList.add(fragment1);

    }
    //adding tabs
    void getTitleList() {
        mFragmentName.add(constants.FRAGMENT_TAB1_NAME);
        mFragmentName.add(constants.FRAGMENT_TAB2_NAME);
    }

    @Override
    public void myClick(Student student,String actionType) {

        FragmentDetail fragmentDetail=(FragmentDetail)mFragmentsList.get(1);
        FragmentList fragmentList = (FragmentList) mFragmentsList.get(0);
          if(actionType.equals(constants.ADD)) {
              mViewPager.setCurrentItem(1);
              fragmentDetail.clearEditText();
          }
        else if(actionType.equals(constants.EDIT))
        {  mViewPager.setCurrentItem(1);
            fragmentDetail.setEditText(student);

        }
        else if(actionType.equals(constants.DELETE))
          {
              fragmentList.deleteItem();
              Toast.makeText(this,getResources().getString(R.string.data_deleted),Toast.LENGTH_LONG).show();
          }

    }
   public void changeTab()
   {
       mViewPager.setCurrentItem(0);
   }
    @Override
    public void onClick(Student student,String actionType) {


        FragmentList fragmentList = (FragmentList) mFragmentsList.get(0);
          if(actionType.equals(constants.ADD) ) {
              Toast.makeText(this, getResources().getString(R.string.data_entered), Toast.LENGTH_LONG).show();
              fragmentList.insert(actionType, student);
          }
          else if(actionType.equals(constants.EDIT)) {
              Toast.makeText(this, getResources().getString(R.string.data_updated), Toast.LENGTH_LONG).show();
              fragmentList.insert(actionType, student);

          }
          else if(actionType.equals(constants.DELETE))
          {
              fragmentList.deleteItem();
          }

       changeTab();

    }

    @Override
    public void setService(String service) {
     FragmentList fragmentList=(FragmentList)mFragmentsList.get(0);
     fragmentList.setService(service);
    }

    @Override
    public void fetchDbList(ArrayList<Student> arrayList, String actionType) {
        FragmentList fragmentList=(FragmentList)mFragmentsList.get(0);
        fragmentList.onFetchStudentList(arrayList);

    }

            @Override
            public void onDbOperationError(String actionType) {
                 if(actionType.equals(constants.ADD))
                     Toast.makeText(this, getResources().getString(R.string.data_not_added), Toast.LENGTH_SHORT).show();
                 else if(actionType.equals(constants.EDIT))
                     Toast.makeText(this,getResources().getString(R.string.data_not_updated),Toast.LENGTH_LONG).show();
                 else if(actionType.equals(constants.DELETE))
                     Toast.makeText(this,getResources().getString(R.string.data_not_deleted),Toast.LENGTH_LONG).show();

            }


        }

