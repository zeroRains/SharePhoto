package com.example.sharephoto.Home;

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


public class HomeFragment extends Fragment {
    ViewHolder vh;
    View homeView;
    String title[] = {"生活", "动漫"};


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (vh == null) {
            vh = new ViewHolder();
            homeView = inflater.inflate(R.layout.fragment_home, container, false);
            vh.tableLayout = homeView.findViewById(R.id.home_tab);
            vh.viewPager = homeView.findViewById(R.id.home_view_pager);
            List<HomePhotoFragment> fragments = new ArrayList<>();
            for (int i = 0; i < title.length; i++) {
                fragments.add(new HomePhotoFragment(title[i]));
                vh.tableLayout.addTab(vh.tableLayout.newTab());
                vh.tableLayout.getTabAt(i).setText(title[i]);
            }
            HomePhotoFragementAdapter adpter = new HomePhotoFragementAdapter(getActivity().getSupportFragmentManager(),fragments,title);
            vh.viewPager.setAdapter(adpter);
        }
        vh.tableLayout.setupWithViewPager(vh.viewPager, false);

        return homeView;
    }




    class ViewHolder {
        TabLayout tableLayout;
        ViewPager viewPager;
    }


}