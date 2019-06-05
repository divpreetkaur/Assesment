package com.example.assignment5.activity;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.assignment5.R;
import com.example.assignment5.adapters.ViewPagerAdapter;
import com.example.assignment5.database.DataBaseHelper;
import com.example.assignment5.fragments.FragmentDetail;
import com.example.assignment5.fragments.FragmentList;
import com.example.assignment5.services.BackgroundTask;
import com.example.assignment5.utilities.Constants;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements FragmentList.MyClickListener,
        FragmentDetail.ClickListener {

    private ViewPagerAdapter mViewPagerAdapter;
    private List<Fragment> mFragmentsList=new ArrayList<>();
    private List<String> mFragmentName = new ArrayList<String>();
    private Constants constants;
    private ViewPager mViewPager;
    private int mTabPosition;
    private String mAction;
    private TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        constants=new Constants();
        mViewPager = (ViewPager) findViewById(R.id.home_vp);
        tabLayout = (TabLayout) findViewById(R.id.tab);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //getting position of tab
                mTabPosition = tab.getPosition();
                if (mTabPosition == 1) {
                    FragmentDetail fragmentDetail =(FragmentDetail)mFragmentsList.get(1);
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

    // adding pages to fragmentlist
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
    public void myClick(Bundle bundle) {
       mViewPager.setCurrentItem(1);
        FragmentDetail fragmentDetail=(FragmentDetail)mFragmentsList.get(1);
        fragmentDetail.clearEditText();
        if(bundle!=null)
        {
           mAction=bundle.getString(constants.ACTION_KEY);
           if(mAction.equals(constants.EDIT))
           {
               fragmentDetail.setEditText(bundle);
           }
        }

    }
   public void changeTab()
   {
       mViewPager.setCurrentItem(0);
   }
    @Override
    public void onClick(Bundle bundle) {


        FragmentList fragmentList = (FragmentList) mFragmentsList.get(0);
        fragmentList.insert(bundle);
       changeTab();

    }

    @Override
    public void getService(String service) {
     FragmentList fragmentList=(FragmentList)mFragmentsList.get(0);
     fragmentList.getService(service);
    }







    }

