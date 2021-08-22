package com.example.sharephoto.Home;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.sharephoto.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;


public class Home extends Fragment {
    ViewHolder vh;
    View home_view;
    String title[] = {"生活", "动漫"};

    public Home() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (vh == null) {
            vh = new ViewHolder();
            home_view = inflater.inflate(R.layout.fragment_home, container, false);
            vh.tableLayout = home_view.findViewById(R.id.home_tab);
            vh.viewPager = home_view.findViewById(R.id.home_view_pager);
            List<HomePhotoFragment> fragments = new ArrayList<>();
            for (int i = 0; i < title.length; i++) {
                fragments.add(new HomePhotoFragment(title[i]));
                vh.tableLayout.addTab(vh.tableLayout.newTab());
                vh.tableLayout.getTabAt(i).setText(title[i]);
            }
            HomePhotoFragementAdapter adapter = new HomePhotoFragementAdapter(getActivity().getSupportFragmentManager(), fragments, title);
            vh.viewPager.setAdapter(adapter);
        }
        vh.tableLayout.setupWithViewPager(vh.viewPager, false);
        return home_view;
    }


    class ViewHolder {
        TabLayout tableLayout;
        ViewPager viewPager;
    }


}