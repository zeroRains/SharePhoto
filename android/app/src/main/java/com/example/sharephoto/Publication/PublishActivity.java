package com.example.sharephoto.Publication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;


import android.Manifest;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.sharephoto.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


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

    public static final int CHOOSE_PHOTO = 2;
    private PublishPhotoAdapter adapter;

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

//        initData();
        adapter = new PublishPhotoAdapter(PublishActivity.this, R.layout.item_publication_photo);
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
                if (ContextCompat.checkSelfPermission(PublishActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{
                            Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1
                    );
                } else {
                    openAlbum();
                }
                break;
            case R.id.publish_submit:
                Toast.makeText(PublishActivity.this, "点击这个发布图片分享", Toast.LENGTH_SHORT).show();
                break;
            case R.id.publish_cancel:
                publish_back(v);
                break;
        }
    }

    // 相册访问
    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    Toast.makeText(this, "拒绝访问图片", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CHOOSE_PHOTO:
                String photo = null;
                if (data == null)
                    break;
                if (requestCode == RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= 19) {
                        photo = handleImageOnKitKat(data);
                    }
                } else {
                    photo = handleImageBeforeKitKat(data);
                }
                new PublishAsyncTask(PublishActivity.this, adapter, photos);
////                这里可以添加
//                PublishPhoto item = new PublishPhoto();
//                item.setPhoto_uri(photo);
//                photos.add(item);
//                adapter.setPhotos(photos);
                break;
            default:
                break;
        }
    }

    private String handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        return imagePath;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private String handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this, uri)) {
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.parseLong(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            imagePath = uri.getPath();
        }
        return imagePath;
    }


    private String getImagePath(Uri externalContentUri, String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(externalContentUri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }
}