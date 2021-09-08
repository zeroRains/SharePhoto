package com.example.sharephoto.Channels;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sharephoto.Detail.DetailActivity;
import com.example.sharephoto.Home.HomePhoto;
import com.example.sharephoto.Home.HomePhotoAdapter;
import com.example.sharephoto.R;
import com.example.sharephoto.RequestConfig;

import java.util.ArrayList;
import java.util.List;

public class ChannelDetailActivity extends AppCompatActivity {
    String title;
    TextView channel_detail_nav_title;
    RecyclerView channel_detail;
    List<HomePhoto> photos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_detail);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.primary));
        }
        title = getIntent().getStringExtra(ChannelFragment.ACTIVITY_NAME);
        initView();
    }

    private void initView() {
        channel_detail_nav_title = findViewById(R.id.channel_detail_nav_text);
        channel_detail = findViewById(R.id.channel_detail);

        channel_detail_nav_title.setText(title);

        initData();
        HomePhotoAdapter photoAdapter = new HomePhotoAdapter(ChannelDetailActivity.this, photos, R.layout.item_home_photo);
        photoAdapter.setOnItemClickListener(new HomePhotoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//                Toast.makeText(getContext(), "" + position, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(view.getContext(), DetailActivity.class);
                startActivity(intent);
            }
        });
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, RecyclerView.VERTICAL);
        channel_detail.setLayoutManager(staggeredGridLayoutManager);
        channel_detail.setAdapter(photoAdapter);
    }

    public void channel_back(View v) {
        ((Activity) v.getContext()).finish();
    }

    private void initData() {
        for (int i = 0; i < 50; i++) {
            HomePhoto item = new HomePhoto();
            item.setIconId("static/imgs/bg03.jpg");
            item.setThumbSnail("static/imgs/bg03.jpg");
//            if (i % 2 == 0)
//                item.setId(R.drawable.nmsl);
//            else
//                item.setId(R.drawable.icon);
            item.setStar("T");
            item.setStarNum(666);
            item.setTag("#休闲时光#");
            item.setUsername("ZeroRains");
            photos.add(item);
        }
    }
}