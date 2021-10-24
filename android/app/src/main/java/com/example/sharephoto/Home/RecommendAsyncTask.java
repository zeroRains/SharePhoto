package com.example.sharephoto.Home;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.sharephoto.RequestConfig;
import com.example.sharephoto.Response.BaseResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RecommendAsyncTask extends AsyncTask<String, Void, String> {
    private Context context;
    private String url;
    private HomePhotoAdapter adapter;
    private SmartRefreshLayout smart;
    private Integer shuoNum;
    List<HomePhoto> photos;

    public RecommendAsyncTask(Context context, String url, HomePhotoAdapter adapter, List<HomePhoto> photos, SmartRefreshLayout swipeRefreshLayout) {
        this.context = context;
        this.url = url;
        this.adapter = adapter;
        this.photos = photos;
        this.smart = swipeRefreshLayout;
    }

    @Override
    protected String doInBackground(String... strings) {
        if (strings.length == 0)
            shuoNum = 10;
        else if (strings.length == 1)
            shuoNum = Integer.valueOf(strings[0]);

//        if (url.contains("concern")) {
//            url = url + "&shuoNum=" + shuoNum;
//        } else {
//            url = url + "?shuoNum=" + shuoNum;
//        }

        Request request = new Request.Builder()
                .url(url+"shuoNum="+shuoNum)
                .get()
                .build();
        Log.d("recommend", "doInBackground: "+request.toString());
//        Log.d("zerorains", "doInBackground: "+url);
        try {
            OkHttpClient client = new OkHttpClient();
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                assert response.body() != null;
                return response.body().string();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        Gson gson = new Gson();
        Type type = new TypeToken<BaseResponse<List<HomePhoto>>>() {
        }.getType();
        BaseResponse<List<HomePhoto>> response = gson.fromJson(s, type);
        if (response.getMsg().equals("success")) {
            Log.d("pommespeter", "onPostExecute: " + response.getData().size());
            if (response.getData().size() <= 10) {
                photos.clear();
                for (HomePhoto item : response.getData()) {
                    photos.add(0, item);
                }
                adapter.setPhotos(photos);
                smart.finishRefresh(1000, true, false);
                smart.finishLoadMore(1000, true, false);
            } else {
                for (HomePhoto item : response.getData()) {
                    photos.add(item);
                }
                adapter.setPhotos(photos);
                smart.finishLoadMore(1000, true, false);
            }
        } else {
            Toast.makeText(context, "请检查网络状态", Toast.LENGTH_SHORT).show();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            smart.finishRefresh(1500, true, true);
            smart.finishLoadMore(1500, true, true);
        }
//        notify();
        super.onPostExecute(s);
    }
}
