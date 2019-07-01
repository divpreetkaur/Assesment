package com.example.assignment5mvp.ui.home;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.assignment5mvp.R;
import com.example.assignment5mvp.constant.Constants;
import com.example.assignment5mvp.data.model.Student;
import com.example.assignment5mvp.ui.home.adapter.ViewPagerAdapter;
import com.example.assignment5mvp.ui.home.callback.DetailFragmentClickListener;
import com.example.assignment5mvp.ui.home.callback.ListFragmentClickListener;
import com.example.assignment5mvp.ui.home.detailfragment.DetailFragment;
import com.example.assignment5mvp.ui.home.listfragment.ListFragment;

import java.util.ArrayList;
import java.util.List;


public class HomeActivity extends AppCompatActivity implements DetailFragmentClickListener, ListFragmentClickListener {
    private ViewPagerAdapter mViewPagerAdapter;
    private List<Fragment> mFragmentsList = new ArrayList<>();
    private List<String> mFragmentName = new ArrayList<String>();
    private TabLayout tabLayout;
    private ViewPager mViewPager;
    private Constants constants;
    private int mTabPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mViewPager = (ViewPager) findViewById(R.id.home_vp);
        tabLayout = (TabLayout) findViewById(R.id.tab);
        constants = new Constants();
        addPagesTofragmentList();
        getTitleList();
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), mFragmentsList, mFragmentName);
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.setCurrentItem(0);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //getting position of tab
                mTabPosition = tab.getPosition();
                if (mTabPosition == 1) {
                    DetailFragment detailFragment= (DetailFragment) mFragmentsList.get(1);
                    detailFragment.clearEditText();


                }
            }


            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
                                           }



    //adding fragments to arraylist
    private void addPagesTofragmentList() {

        mFragmentsList = new ArrayList<>();
        Fragment fragment = new ListFragment();
        ((ListFragment) fragment).instantiateListener(this);
        mFragmentsList.add(fragment);
        Fragment fragment1 = new DetailFragment();
        ((DetailFragment) fragment1).instantiateListener(this);
        mFragmentsList.add(fragment1);

    }

    //adding tabs
    void getTitleList() {
        mFragmentName.add(constants.FRAGMENT_TAB1_NAME);
        mFragmentName.add(constants.FRAGMENT_TAB2_NAME);
    }


    @Override
    public void myClick(String actionType, Student student) {
        ListFragment listFragment = (ListFragment) mFragmentsList.get(0);
        if (actionType.equals(constants.ADD) || actionType.equals(constants.EDIT))
        {
            mViewPager.setCurrentItem(0);
            listFragment.insertData(actionType, student);
        }
        else if(actionType.equals(constants.DELETE))
        {
            listFragment.deleteData();
        }

    }

    @Override
    public void setService(String service) {
        ListFragment listFragment = (ListFragment) mFragmentsList.get(0);
        listFragment.setService(service);
    }

    @Override
    public void onDBoperationError(String actionType) {
        if(actionType.equals(constants.ADD))
        {
            Toast.makeText(this,getResources().getString(R.string.add_data_fail),Toast.LENGTH_SHORT).show();

        }
        else if(actionType.equals(constants.EDIT))
        {
            Toast.makeText(this,getResources().getString(R.string.update_data_fail),Toast.LENGTH_SHORT).show();

        }
       else if(actionType.equals(constants.DELETE))
        {
            Toast.makeText(this,getResources().getString(R.string.delete_data_fail),Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    public void fetchDBList(ArrayList<Student> arrayList) {
        ListFragment listFragment=(ListFragment)mFragmentsList.get(0);
        listFragment.onFetchStudentList(arrayList);

    }

    @Override
    public void onClick(String actionType, Student student) {
        mViewPager.setCurrentItem(1);
        DetailFragment detailFragment = (DetailFragment) mFragmentsList.get(1);
        if (actionType.equals(constants.ADD)) {
            detailFragment.clearEditText();
        } else if (actionType.equals(constants.EDIT)) {
            detailFragment.setEditText(student);
        }
    }
}

