package com.example.sharephoto.Home;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

public class HomePhotoFragementAdapter extends FragmentPagerAdapter {
    List<HomePhotoFragment> fragementList = new ArrayList<>();
    String title[];
    FragmentManager fragmentManager;

    public HomePhotoFragementAdapter(FragmentManager fm, List<HomePhotoFragment> fragmentList, String[] title) {
        super(fm);
        this.fragementList = fragmentList;
        this.title = title;
        this.fragmentManager = fm;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragementList.get(position);
    }

    @Override
    public int getCount() {
        return fragementList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }
}
