package com.example.sharephoto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;


import android.app.Activity;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


public class PublishActivity extends AppCompatActivity implements View.OnClickListener {
    //    图像选择
    RecyclerView publish_photo;
    ImageView publish_add;

    //    标题
    EditText publish_title;
    //    正文
    EditText publish_content;
    //    话题
    EditText publish_topic;

    //    提交栏
    Button publish_submit;
    ImageView publish_cancel;

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

        publish_add.setOnClickListener(this);
        publish_cancel.setOnClickListener(this);
        publish_submit.setOnClickListener(this);
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