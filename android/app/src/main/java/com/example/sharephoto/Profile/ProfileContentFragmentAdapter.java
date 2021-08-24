package com.example.sharephoto.Profile;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

class ProfileContentFragmentAdapter extends FragmentPagerAdapter {

    private List<ProfileContentFragment> fragmentList;
    private String[] tabTitle;
    private FragmentManager fm;

    public ProfileContentFragmentAdapter(@NonNull FragmentManager fm, List<ProfileContentFragment> fragmentList, String[] tabTitle) {
        super(fm);
        this.fragmentList = fragmentList;
        this.tabTitle = tabTitle;
        this.fm = fm;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitle[position];
    }
}
