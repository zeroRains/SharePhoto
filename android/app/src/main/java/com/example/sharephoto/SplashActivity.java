package com.example.sharephoto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SplashActivity extends AppCompatActivity {

    public OkHttpClient okHttpClient = new OkHttpClient();
    public OkHttpClient client = okHttpClient.newBuilder().build();
    public String baseUrl = "https://baidu.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_splash);
//        getSplash();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                SplashActivity.this.finish();
            }
        }, 2000);
    }

    private void getSplash() {
        // get请求
        // 创建一个request对象
        Request request = new Request.Builder().url(baseUrl).build();
        // 执行和回调
        client.newCall(request).enqueue(new Callback() {
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                // 请求失败
            }

            public void onResponse(@NonNull Call call, @NonNull Response response)
                    throws IOException {
                // 请求成功
                String ret = response.body().string();
                new Handler(Looper.getMainLooper()).post(() -> Toast.makeText(SplashActivity.this, "连接服务器成功啦 ヾ(≧▽≦*)o", Toast.LENGTH_SHORT).show());
//                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
//                startActivity(intent);
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
    }
}