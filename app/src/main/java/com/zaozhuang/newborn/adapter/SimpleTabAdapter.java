package com.zaozhuang.newborn.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class SimpleTabAdapter extends FragmentPagerAdapter {

    private FragmentManager fm;
    private List<Fragment> fragments;
    private List<String> titles;

    public SimpleTabAdapter(FragmentManager fm) {
        super(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.fm = fm;
        fragments = new ArrayList<>();
        titles = new ArrayList<>();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        fm.beginTransaction().show(fragment).commitAllowingStateLoss();
        return fragment;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        fm.beginTransaction().hide(fragments.get(position)).commitAllowingStateLoss();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

    public void addFragment(Fragment fragment, String title) {
        fragments.add(fragment);
        titles.add(title);
    }

    public void addFragment(Fragment fragment, String title, int index) {
        fragments.add(index, fragment);
        titles.add(index, title);
    }

    public void remove(int index) {
        if (index >= 0 && index < fragments.size()) {
            fragments.remove(index);
            titles.remove(index);
        }
    }
}
