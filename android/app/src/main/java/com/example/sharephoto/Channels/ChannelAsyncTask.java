package com.example.sharephoto.Channels;

import android.content.Context;
import android.os.AsyncTask;

import com.example.sharephoto.Home.HomePhoto;
import com.example.sharephoto.Home.HomePhotoAdapter;
import com.example.sharephoto.RequestConfig;
import com.example.sharephoto.Response.BaseResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ChannelAsyncTask extends AsyncTask<String, Void, String> {
    private Context context;
    private HomePhotoAdapter adapter;
    private List<HomePhoto> photos = new ArrayList<>();

    public ChannelAsyncTask(Context context, HomePhotoAdapter adapter, List<HomePhoto> photos) {
        this.context = context;
        this.adapter = adapter;
        this.photos = photos;
    }

    @Override
    protected String doInBackground(String... strings) {
        String category = strings[0];
        String user = strings[1];
        Request request = new Request.Builder()
                .get()
                .url(RequestConfig.CATEGORY + "?category=" + category + "&user=" + user)
                .build();
        OkHttpClient client = new OkHttpClient();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        Gson gson = new Gson();
        Type type = new TypeToken<BaseResponse<List<HomePhoto>>>() {
        }.getType();
        BaseResponse<List<HomePhoto>> res = gson.fromJson(s, type);
        for (HomePhoto item : res.getData()) {
            photos.add(0, item);
        }
        adapter.setPhotos(photos);
        super.onPostExecute(s);
    }
}
