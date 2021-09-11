package com.example.sharephoto.Home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.sharephoto.Detail.DetailActivity;
import com.example.sharephoto.R;
import com.example.sharephoto.RequestConfig;


import java.util.ArrayList;
import java.util.List;


public class HomePhotoFragment extends Fragment {
    List<HomePhoto> photos = new ArrayList<>();
    private String status;
    private String URL;
    private HomePhotoAdapter photoAdapter;
    private View rootView;

    public HomePhotoFragment(String status) {
        this.status = status;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_home_photo, container, false);
        }
        View view = rootView;
        RecyclerView recyclerView = view.findViewById(R.id.home_photo_show);
        photoAdapter = new HomePhotoAdapter(getContext(), photos, R.layout.item_home_photo);
        photoAdapter.setOnItemClickListener(new HomePhotoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getContext(), "" + photos.get(position).getId(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(view.getContext(), DetailActivity.class);
                intent.putExtra("shuoshuoId", photos.get(position).getId());
                startActivity(intent);
            }
        });
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setAdapter(photoAdapter);
        initData();
        return view;
    }

    private void initData() {
        switch (status) {
            case "推荐":
                this.URL = RequestConfig.RECOMMEND;
                break;
            case "关注":
                String id = getContext().getSharedPreferences("data", Context.MODE_PRIVATE).getString("username", "");
                this.URL = RequestConfig.CONCERN + "?id=" + id;
                break;
            default:
                this.URL = null;
                break;
        }
        new RecommendAsyncTask(rootView.getContext(), URL, photoAdapter, photos).execute();
//        for (int i = 0; i < 50; i++) {
//            HomePhoto item = new HomePhoto();
////            item.setIconId(RequestConfig.URL+"static/imgs/bg03.jpg");
//            item.setThumbSnail("static/imgs/bg03.jpg");
////            if (i % 2 == 0)
////                item.setId(R.drawable.nmsl);
////            else
////                item.setId(R.drawable.icon);
//            item.setIconId("static/imgs/bg03.jpg");
//            item.setStar("T");
//            item.setStarNum(666);
//            item.setTag("#休闲时光#");
//            item.setUsername("ZeroRains");
//            photos.add(item);
//        }
    }
}