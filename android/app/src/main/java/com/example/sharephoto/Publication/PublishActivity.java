package com.example.sharephoto.Publication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;


import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.sharephoto.R;

import java.util.ArrayList;
import java.util.List;


public class PublishActivity extends AppCompatActivity implements View.OnClickListener {
    //    图像选择
    private RecyclerView publish_photo;
    private ImageView publish_add;

    //    标题
    private EditText publish_title;
    //    正文
    private EditText publish_content;
    //    话题
    private EditText publish_topic;

    //    提交栏
    private Button publish_submit;
    private ImageView publish_cancel;

    //    候选图片
    private List<PublishPhoto> photos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.primary));
        }
        initView();
    }

    private void initView() {
//        图像选择
        publish_photo = findViewById(R.id.publish_photo);
        publish_add = findViewById(R.id.publish_add);

//        标题正文话题
        publish_title = findViewById(R.id.publish_title);
        publish_content = findViewById(R.id.publish_content);
        publish_topic = findViewById(R.id.publish_topic);

//        提交栏
        publish_submit = findViewById(R.id.publish_submit);
        publish_cancel = findViewById(R.id.publish_cancel);

//        添加点击事件
        publish_add.setOnClickListener(this);
        publish_cancel.setOnClickListener(this);
        publish_submit.setOnClickListener(this);

        initData();
        PublishPhotoAdapter adapter = new PublishPhotoAdapter(PublishActivity.this, photos, R.layout.item_publication_photo);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(1, RecyclerView.HORIZONTAL);
        publish_photo.setAdapter(adapter);
        publish_photo.setLayoutManager(staggeredGridLayoutManager);
    }

    private void initData() {
        for (int i = 0; i < 7; i++) {
            PublishPhoto photo = new PublishPhoto();
            photo.setId(R.drawable.icon);
            photos.add(photo);
        }
    }

    public void publish_back(View v) {
        ((Activity) v.getContext()).finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.publish_add:
                Toast.makeText(PublishActivity.this, "点击这个按钮开始添加图片", Toast.LENGTH_SHORT).show();
                break;
            case R.id.publish_submit:
                Toast.makeText(PublishActivity.this, "点击这个发布图片分享", Toast.LENGTH_SHORT).show();
                break;
            case R.id.publish_cancel:
                publish_back(v);
                break;
        }
    }
}