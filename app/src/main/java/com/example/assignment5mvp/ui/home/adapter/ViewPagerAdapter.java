package com.example.assignment5mvp.ui.home.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> fragments;
    private List<String>fragmentname=new ArrayList<String>();

    public ViewPagerAdapter(final FragmentManager fm, final List<Fragment> fragments, final List<String> fragmentname) {
        super(fm);
        this.fragments = fragments;
        this.fragmentname=fragmentname;
    }
    @Override
    public Fragment getItem(final int position) {
        return this.fragments.get(position);
    }
    @Override
    public int getCount() {
        if (fragments == null) {
            return 0;
        } else {
            return fragments.size();
        }
    }


    @Override
    public CharSequence getPageTitle(int position)
    {
        return this.fragmentname.get(position);
    }









}
