package com.example.sharephoto.Home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sharephoto.R;


import java.util.ArrayList;
import java.util.List;


public class HomePhotoFragment extends Fragment {
    List<HomePhoto> photos = new ArrayList<>();
    private String status;

    public HomePhotoFragment(String status) {
        this.status = status;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_photo, container, false);
        initData();
        RecyclerView recyclerView = view.findViewById(R.id.home_photo_show);
        HomePhotoAdapter photoAdapter = new HomePhotoAdapter(getContext(), photos, R.layout.home_photo_item);
        photoAdapter.setOnItemClickListener(new HomePhotoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getContext(),""+position,Toast.LENGTH_SHORT).show();
            }
        });
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setAdapter(photoAdapter);

        return view;
    }

    private void initData() {
        for (int i = 0; i < 50; i++) {
            HomePhoto item = new HomePhoto();
            item.setIcon_id(R.drawable.icon);
            if(i%2==0)
                item.setPhoto_id(R.drawable.nmsl);
            else
                item.setPhoto_id(R.drawable.icon);
            item.setStart(true);
            item.setStart_num(666);
            item.setTag("#休闲时光#");
            item.setUsername("ZeroRains");
            photos.add(item);
        }
    }
}