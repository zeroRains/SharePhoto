package com.example.sharephoto.Home;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.sharephoto.Response.BaseResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RecommendAsyncTask extends AsyncTask<Void, Void, String> {
    private Context context;
    private String url;
    private HomePhotoAdapter adapter;
    List<HomePhoto> photos;

    public RecommendAsyncTask(Context context, String url, HomePhotoAdapter adapter, List<HomePhoto> photos) {
        this.context = context;
        this.url = url;
        this.adapter = adapter;
        this.photos = photos;
    }

    @Override
    protected String doInBackground(Void... voids) {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        try {
            OkHttpClient client = new OkHttpClient();
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
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
            for (HomePhoto item : response.getData()) {
                photos.add(0, item);
            }
            adapter.setPhotos(photos);
        } else {
            Toast.makeText(context, "请检查网络状态", Toast.LENGTH_SHORT).show();
        }
        super.onPostExecute(s);
    }
}
