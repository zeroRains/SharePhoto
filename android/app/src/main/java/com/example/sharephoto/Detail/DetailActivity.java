package com.example.sharephoto.Detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sharephoto.R;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {
    //    作者栏
    ImageView detail_icon;
    TextView detail_username;
    TextView detail_time;
    Button detail_follow;

    //    图片区
    ImageView detail_photo;

    //    状态区
    ImageView detail_zan_status;
    TextView detail_zan_num;
    ImageView detail_love_status;
    TextView detail_love_num;
    ImageView detail_transition;

    // 描述区
    TextView detail_description_title;
    TextView detail_description;

    //    评论区
    RecyclerView detail_remark;
    List<Remark> remarks = new ArrayList<>();

    //    发表评论区
    EditText detail_remark_content;
    Button detail_remark_submit;


    private RemarkAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.primary));
        }
        initView();
        initData();
    }

    private void initView() {
//        作者
        detail_icon = findViewById(R.id.detail_icon);
        detail_username = findViewById(R.id.detail_username);
        detail_time = findViewById(R.id.detail_time);
        detail_follow = findViewById(R.id.detail_follow);

//        图片
        detail_photo = findViewById(R.id.detail_photo);

//        状态
        detail_zan_status = findViewById(R.id.detail_zan_status);
        detail_zan_num = findViewById(R.id.detail_zan_num);
        detail_love_status = findViewById(R.id.detail_love_status);
        detail_love_num = findViewById(R.id.detail_love_num);
        detail_transition = findViewById(R.id.detail_transition);

//        描述
        detail_description_title = findViewById(R.id.detail_description_title);
        detail_description = findViewById(R.id.detail_description);

//        评论
        detail_remark = findViewById(R.id.detail_remark);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        detail_remark.setLayoutManager(staggeredGridLayoutManager);

        adapter = new RemarkAdapter(DetailActivity.this, R.layout.item_remark);
        detail_remark.setAdapter(adapter);

//        adapter.setRemarks(remarks);


//        发表评论
        detail_remark_content = findViewById(R.id.detail_remark_content);
        detail_remark_submit = findViewById(R.id.detail_remark_submit);

        setClickEvent();
    }

    private void initData() {
        new RemarkAsyncTask(this, remarks, adapter).execute("1");
//        for (int i = 0; i < 10; i++) {
//            Remark remark = new Remark();
//            remark.setIcon(R.drawable.icon);
//            remark.setUsername("ZeroRains");
//            remark.setContent("这是很多中肯的评论集合，这是很多中肯的评论集合，这是很多中肯的评论集合，这是很多中肯的评论集合");
//            remark.setDate("2021-8-24");
//            remark.setNum(666);
//            remark.setStatus(false);
//            remarks.add(remark);
//        }
    }

    private void setClickEvent() {
        detail_icon.setOnClickListener(this);
        detail_follow.setOnClickListener(this);
        detail_zan_status.setOnClickListener(this);
        detail_love_status.setOnClickListener(this);
        detail_transition.setOnClickListener(this);
        detail_remark_submit.setOnClickListener(this);
    }

    public void detail_back(View view) {
        ((Activity) view.getContext()).finish();
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.detail_icon:
                Toast.makeText(DetailActivity.this, "点击这个会到别人的主页去", Toast.LENGTH_SHORT).show();
                break;
            case R.id.detail_follow:
                if (detail_follow.isSelected()) {
                    detail_follow.setSelected(false);
                    detail_follow.setText("+ 关注");
                    detail_follow.setTextColor(getResources().getColor(R.color.primary));
                    Toast.makeText(DetailActivity.this, "已经取消关注了", Toast.LENGTH_SHORT).show();
                } else {
                    detail_follow.setSelected(true);
                    detail_follow.setText("✓ 已关注");
                    detail_follow.setTextColor(getResources().getColor(R.color.white));
                    Toast.makeText(DetailActivity.this, "关注成功", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.detail_zan_status:
                if (detail_zan_status.isSelected()) {
                    detail_zan_status.setSelected(false);
                    int num = Integer.parseInt(detail_zan_num.getText().toString());
                    num = num - 1;
                    detail_zan_num.setText(num + "");
                    Toast.makeText(DetailActivity.this, "取消点赞", Toast.LENGTH_SHORT).show();
                } else {
                    detail_zan_status.setSelected(true);
                    int num = Integer.parseInt(detail_zan_num.getText().toString());
                    num = num + 1;
                    detail_zan_num.setText(num + "");
                    Toast.makeText(DetailActivity.this, "点赞+1", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.detail_love_status:
                if (detail_love_status.isSelected()) {
                    detail_love_status.setSelected(false);
                    int num = Integer.parseInt(detail_love_num.getText().toString());
                    num -= 1;
                    detail_love_num.setText("" + num);
                    Toast.makeText(DetailActivity.this, "取消收藏", Toast.LENGTH_SHORT).show();
                } else {
                    detail_love_status.setSelected(true);
                    int num = Integer.parseInt(detail_love_num.getText().toString());
                    num += 1;
                    detail_love_num.setText("" + num);
                    Toast.makeText(DetailActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.detail_transition:
                Toast.makeText(DetailActivity.this, "可以转发了", Toast.LENGTH_SHORT).show();
                break;
            case R.id.detail_remark_submit:
                Toast.makeText(DetailActivity.this, detail_remark_content.getText().toString(), Toast.LENGTH_SHORT).show();
                break;
        }

    }
}