package com.example.sharephoto.Profile;

import android.os.AsyncTask;

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

public class GetPhotoAsyncTask extends AsyncTask<String, Void, String> {
    private String URL;
    private List<Profile> photos = new ArrayList<>();
    private ProfileContentAdapter adapter;

    public GetPhotoAsyncTask(String URL, List<Profile> photos, ProfileContentAdapter adapter) {
        this.URL = URL;
        this.photos = photos;
        this.adapter = adapter;
    }


    @Override
    protected String doInBackground(String... strings) {
        String user = strings[0];
        Request request = new Request.Builder()
                .url(URL + "?user=" + user)
                .get()
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
        Type type = new TypeToken<BaseResponse<List<Profile>>>() {
        }.getType();
        BaseResponse<List<Profile>> res = gson.fromJson(s, type);
        if (res.getMsg().equals("success")) {
            for (Profile item : res.getData()) {
                photos.add(0, item);
            }
            adapter.setContentList(photos);
        }
        super.onPostExecute(s);
    }
}
