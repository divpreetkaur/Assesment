package com.example.assignment4.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.assignment4.R;
import com.example.assignment4.adapters.ViewPagerAdapter;
import com.example.assignment4.fragments.FragmentDetail;
import com.example.assignment4.fragments.FragmentList;
import com.example.assignment4.utilities.constants;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements FragmentList.MyClickListener, FragmentDetail.ClickListener {
    ViewPagerAdapter viewPagerAdapter;
    List<Fragment> fragmentsList;
    List<String> fragmentName = new ArrayList<String>();
    ViewPager viewPager;
    int tabPosition;
    constants constant=new constants();
    Fragment fragment =new FragmentList();
    TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        viewPager = (ViewPager) findViewById(R.id.home_vp);
        tabLayout = (TabLayout) findViewById(R.id.tab);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //getting position of tab
                tabPosition = tab.getPosition();
                if (tabPosition == 1) {

                       FragmentDetail fragmentDetail =(FragmentDetail)fragmentsList.get(1);
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
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragmentsList, fragmentName);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setCurrentItem(0);
        tabLayout.setupWithViewPager(viewPager);
    }

    // adding pages to fragmentlist
    private void addPagesTofragmentList() {

        fragmentsList = new ArrayList<>();

        Fragment fragment = new FragmentList();
        ((FragmentList) fragment).instantiateListener(this);
        fragmentsList.add(fragment);
        Fragment fragment1 = new FragmentDetail();
        ((FragmentDetail) fragment1).instantiate(this);
        fragmentsList.add(fragment1);
    }
    //adding tabs
    void getTitleList() {
        fragmentName.add(constant.FRAGMENT_TAB1_NAME);
        fragmentName.add(constant.FRAGMENT_TAB2_NAME);
    }
    //to open second fragment
    @Override
    public void myClick(String action,Bundle bundle) {

        viewPager.setCurrentItem(1);
              if(action.equals(getString(R.string.edit))) {
                  FragmentDetail fragmentDetail = (FragmentDetail) fragmentsList.get(1);
                  fragmentDetail.setEditText(bundle);
              }
              else
              {
                  FragmentDetail fragmentDetail =(FragmentDetail)fragmentsList.get(1);
                  fragmentDetail.clearEditText();
              }

              }
      // to open first  fragment from second fragment
              @Override
    public void onClick(String name, String rollno, String cls) {


        viewPager.setCurrentItem(0);
        FragmentList fragment =(FragmentList) fragmentsList.get(0);
        fragment.getData(name,rollno,cls);
            }


        }





