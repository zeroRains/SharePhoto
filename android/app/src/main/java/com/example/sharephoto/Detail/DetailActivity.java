package com.example.sharephoto.Detail;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
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

import com.example.sharephoto.Home.DeleteAsyncTask;
import com.example.sharephoto.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {
    // 说说内容
    ViewHolder viewHolder = new ViewHolder();

    //    评论区
    RecyclerView detail_remark;
    List<Remark> remarks = new ArrayList<>();

    //    发表评论区
    EditText detail_remark_content;
    Button detail_remark_submit;
    Activity context;


    private RemarkAdapter adapter;
    private DetailImageAdapter imageAdapter;
    private int shuoshuoId = -1;

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
        context = this;
    }

    private void initView() {
//        作者
        viewHolder.detail_icon = findViewById(R.id.detail_icon);
        viewHolder.detail_username = findViewById(R.id.detail_username);
        viewHolder.detail_time = findViewById(R.id.detail_time);
        viewHolder.detail_follow = findViewById(R.id.detail_follow);
        viewHolder.detail_delete = findViewById(R.id.detail_delete);

//        图片
        viewHolder.detail_photo = findViewById(R.id.detail_img_list);

//        状态
        viewHolder.detail_zan_status = findViewById(R.id.detail_zan_status);
        viewHolder.detail_zan_num = findViewById(R.id.detail_zan_num);
        viewHolder.detail_love_status = findViewById(R.id.detail_love_status);
        viewHolder.detail_love_num = findViewById(R.id.detail_love_num);
        viewHolder.detail_transition = findViewById(R.id.detail_transition);

//        描述
        viewHolder.detail_description_title = findViewById(R.id.detail_description_title);
        viewHolder.detail_description = findViewById(R.id.detail_description);

//        评论
        detail_remark = findViewById(R.id.detail_remark);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        detail_remark.setLayoutManager(staggeredGridLayoutManager);

        adapter = new RemarkAdapter(DetailActivity.this, R.layout.item_remark);
        detail_remark.setAdapter(adapter);

        imageAdapter = new DetailImageAdapter(DetailActivity.this, new String[]{}, R.layout.item_detail_img);
        StaggeredGridLayoutManager imgLayoutManager = new StaggeredGridLayoutManager(1, RecyclerView.HORIZONTAL);
        viewHolder.detail_photo.setAdapter(imageAdapter);
        viewHolder.detail_photo.setLayoutManager(imgLayoutManager);
//        adapter.setRemarks(remarks);


//        发表评论
        detail_remark_content = findViewById(R.id.detail_remark_content);
        detail_remark_submit = findViewById(R.id.detail_remark_submit);

        setClickEvent();

        shuoshuoId = getIntent().getIntExtra("shuoshuoId", -1);

    }

    private void initData() {
        String id = getSharedPreferences("data", MODE_PRIVATE).getString("username", "");
        if (!id.equals("") && shuoshuoId != -1) {
            new RemarkAsyncTask(this, remarks, adapter, viewHolder, imageAdapter).execute(shuoshuoId + "", id);
        }
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
        viewHolder.detail_icon.setOnClickListener(this);
        viewHolder.detail_follow.setOnClickListener(this);
        viewHolder.detail_delete.setOnClickListener(this);
        viewHolder.detail_zan_status.setOnClickListener(this);
        viewHolder.detail_love_status.setOnClickListener(this);
        viewHolder.detail_transition.setOnClickListener(this);
        detail_remark_submit.setOnClickListener(this);
    }

    public void detail_back(View view) {
        ((Activity) view.getContext()).finish();
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        String uid = getSharedPreferences("data", MODE_PRIVATE).getString("username", "");
        switch (id) {
            case R.id.detail_icon:
                Toast.makeText(DetailActivity.this, "点击这个会到别人的主页去", Toast.LENGTH_SHORT).show();
                break;
            case R.id.detail_follow:
                String status;
                if (viewHolder.detail_follow.isSelected()) {
                    viewHolder.detail_follow.setSelected(false);
                    viewHolder.detail_follow.setText("+ 关注");
                    viewHolder.detail_follow.setTextColor(getResources().getColor(R.color.primary));
                    status = "false";
//                    Toast.makeText(DetailActivity.this, "已经取消关注了", Toast.LENGTH_SHORT).show();
                } else {
                    viewHolder.detail_follow.setSelected(true);
                    viewHolder.detail_follow.setText("✓ 已关注");
                    viewHolder.detail_follow.setTextColor(getResources().getColor(R.color.white));
                    status = "true";
//                    Toast.makeText(DetailActivity.this, "关注成功", Toast.LENGTH_SHORT).show();
                }

                if (shuoshuoId != -1 && !uid.equals(""))
                    new FollowAuthorAsyncTask().execute(shuoshuoId + "", uid, viewHolder.detail_username.getText().toString(), status);
                break;
            case R.id.detail_zan_status:
                String status_zan;
                if (viewHolder.detail_zan_status.isSelected()) {
                    viewHolder.detail_zan_status.setSelected(false);
                    int num = Integer.parseInt(viewHolder.detail_zan_num.getText().toString());
                    num = num - 1;
                    viewHolder.detail_zan_num.setText(num + "");
//                    Toast.makeText(DetailActivity.this, "取消点赞", Toast.LENGTH_SHORT).show();
                    status_zan = "false";
                } else {
                    viewHolder.detail_zan_status.setSelected(true);
                    int num = Integer.parseInt(viewHolder.detail_zan_num.getText().toString());
                    num = num + 1;
                    viewHolder.detail_zan_num.setText(num + "");
//                    Toast.makeText(DetailActivity.this, "点赞+1", Toast.LENGTH_SHORT).show();
                    status_zan = "true";
                }
                if (shuoshuoId != -1 && !uid.equals("")) {
//                    Log.d("zero", "onClick: " + shuoshuoId);
                    new ThumbsupAsyncTask().execute(shuoshuoId + "", uid, status_zan);
                }
                break;
            case R.id.detail_love_status:
                String status_star;
                if (viewHolder.detail_love_status.isSelected()) {
                    viewHolder.detail_love_status.setSelected(false);
                    int num = Integer.parseInt(viewHolder.detail_love_num.getText().toString());
                    num -= 1;
                    viewHolder.detail_love_num.setText("" + num);
//                    Toast.makeText(DetailActivity.this, "取消收藏", Toast.LENGTH_SHORT).show();
                    status_star = "false";
                } else {
                    viewHolder.detail_love_status.setSelected(true);
                    int num = Integer.parseInt(viewHolder.detail_love_num.getText().toString());
                    num += 1;
                    viewHolder.detail_love_num.setText("" + num);
//                    Toast.makeText(DetailActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
                    status_star = "true";
                }
                if (shuoshuoId != -1 && !uid.equals("")) {
                    new StarAsyncTask().execute("" + shuoshuoId, uid, status_star);
                }
                break;
            case R.id.detail_transition:
                Toast.makeText(DetailActivity.this, "可以转发了", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(imageAdapter.getPhotos()[0])));
                intent.setType("*/*");
                context.startActivity(Intent.createChooser(intent, "分享"));

                break;
            case R.id.detail_remark_submit:
                if (shuoshuoId != -1 && !uid.equals("")) {
                    new RemarkSubmitAsyncTask(DetailActivity.this).execute("" + shuoshuoId, uid, detail_remark_content.getText().toString());
                }
                detail_remark_content.setText("");
                break;
            case R.id.detail_delete:
                Toast.makeText(DetailActivity.this,"删除",Toast.LENGTH_SHORT).show();
                new DeleteAsyncTask(DetailActivity.this).execute(String.valueOf(shuoshuoId));
//                context.finish();
                break;
        }

    }

    public class ViewHolder {
        //    作者栏
        ImageView detail_icon;
        TextView detail_username;
        TextView detail_time;
        Button detail_follow;
        Button detail_delete;

        //    图片区
        RecyclerView detail_photo;

        //    状态区
        ImageView detail_zan_status;
        TextView detail_zan_num;
        ImageView detail_love_status;
        TextView detail_love_num;
        ImageView detail_transition;

        // 描述区
        TextView detail_description_title;
        TextView detail_description;
    }
}