package com.example.sharephoto.Profile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sharephoto.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProfileFragment extends Fragment {

    private static final String ARG_PARAM1 = "label";
    private String[] tabTitle = {"动态", "收藏", "赞过"};
    private ViewHolder vh;
    private View profileView;

    private List<ProfileContentFragment> fragmentList;
    private ProfileContentFragmentAdapter adapter;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance(String param1) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (vh == null) {
            vh = new ViewHolder();
            fragmentList = new ArrayList<>();

            profileView = inflater.inflate(R.layout.fragment_profile, container, false);
            vh.profileTab = profileView.findViewById(R.id.profile_tab);
            vh.viewPager = profileView.findViewById(R.id.profile_view_pager);

            for (String t : tabTitle) {
                vh.profileTab.addTab(vh.profileTab.newTab().setText(t));
                fragmentList.add(ProfileContentFragment.newInstance(t));
            }
            adapter = new ProfileContentFragmentAdapter(requireActivity().getSupportFragmentManager(), fragmentList, tabTitle);
            vh.viewPager.setAdapter(adapter);
        }
        vh.profileTab.setupWithViewPager(vh.viewPager, false);
        return profileView;
    }

    static class ViewHolder {
        TabLayout profileTab;
        ViewPager viewPager;
    }
}