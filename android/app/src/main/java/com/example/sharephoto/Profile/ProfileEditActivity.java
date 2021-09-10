package com.example.sharephoto.Profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.sharephoto.R;
import com.example.sharephoto.RequestConfig;
import com.example.sharephoto.Response.BaseResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ProfileEditActivity extends AppCompatActivity {
    private CircleImageView edit_icon;
    private EditText edit_username;
    private Spinner edit_sex;
    private EditText edit_info;
    private Button logout;

    public static final String ICON = "icon";
    public static final String USERNAME = "username";
    public static final String SEX = "sex";
    public static final String INFO = "info";
    public static final int LOGOUT = 886;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.primary));
        }
        initView();
        initData();
    }

    private void initData() {
        String id = getSharedPreferences("data", MODE_PRIVATE).getString("username", "");
        if (!id.equals("")) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Request request = new Request.Builder()
                            .get()
                            .url(GetInfoAsyncTask.URL + "?id=" + id)
                            .build();
                    OkHttpClient client = new OkHttpClient();
                    try {
                        Response response = client.newCall(request).execute();
                        if (response.isSuccessful()) {
                            String s = response.body().string();
                            Gson gson = new Gson();
                            Type type = new TypeToken<BaseResponse<List<ProfileInfoResponse>>>() {
                            }.getType();
                            BaseResponse<List<ProfileInfoResponse>> res = gson.fromJson(s, type);
                            ProfileInfoResponse info = res.getData().get(0);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Glide.with(ProfileEditActivity.this)
                                            .load(RequestConfig.URL + info.getUrl())
                                            .into(edit_icon);
                                    edit_username.setText(info.getUsername());
                                    edit_sex.setSelection(info.getSex().equals("女") ? 1 : 0, true);
                                    edit_info.setText(info.getIntroduction());
                                }
                            });
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    private void initView() {
        edit_icon = findViewById(R.id.profile_edit_icon);
        edit_username = findViewById(R.id.profile_edit_username);
        edit_sex = findViewById(R.id.profile_edit_sex);
        edit_info = findViewById(R.id.profile_edit_info);
        logout = findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
                editor.putString("username", "");
                editor.apply();
                Intent intent = new Intent();
                setResult(LOGOUT, intent);
                ((Activity) v.getContext()).finish();
            }
        });

        edit_sex.setSelection(0, true);
    }

    public void edit_back(View v) {
//        Toast.makeText(ProfileEditActivity.this, edit_username.getText().toString() + edit_sex.getSelectedItem().toString() + edit_info.getText().toString(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        intent.putExtra(USERNAME, edit_username.getText().toString());
        intent.putExtra(SEX, edit_sex.getSelectedItem().toString());
        intent.putExtra(INFO, edit_info.getText().toString());
        intent.putExtra(ICON, R.drawable.icon);
        setResult(RESULT_OK, intent);
        ((Activity) v.getContext()).finish();
    }
}