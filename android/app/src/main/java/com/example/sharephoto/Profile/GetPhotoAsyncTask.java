package com.example.sharephoto.Profile;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.sharephoto.Response.BaseResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetPhotoAsyncTask extends AsyncTask<String, Void, String> {
    private String URL;
    private Context context;
    private List<Profile> photos = new ArrayList<>();
    private ProfileContentAdapter adapter;
    private Integer currentNum;
    private String mode;

    private SmartRefreshLayout smart;

    public GetPhotoAsyncTask(Context context, String URL, List<Profile> photos, ProfileContentAdapter adapter, Integer currentNum, String mode, SmartRefreshLayout smart) {
        this.context = context;
        this.URL = URL;
        this.photos = photos;
        this.adapter = adapter;
        this.currentNum = currentNum;
        this.mode = mode;
        this.smart = smart;
    }


    @Override
    protected String doInBackground(String... strings) {
        String user = strings[0];
        Request request = new Request.Builder()
                .url(URL + "?user=" + user)
                .get()
                .build();
        Log.d("zerorains", "get Photo doInBackground: "+URL + "?user=" + user);
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
        int count = 0;
        if (res.getMsg().equals("success")) {
            if (mode.equals("refresh")) {
                photos.clear();
                for (Profile item : res.getData()) {
                    photos.add(0, item);
                    count++;
                    if (count == currentNum ) {
                        if (count >= res.getData().size()) {
                            smart.finishRefresh(1500, true, true);
                        }
                        break;
                    }
                }
                adapter.setContentList(photos);
                smart.finishRefresh(1000, true, false);
                smart.finishLoadMore(1000, true, false);
            } else if (mode.equals("loadMore")){
                for (Profile item : res.getData()) {
                    photos.add(item);
                    count++;
                    if (count == currentNum ) {
                        if (count >= res.getData().size()) {
                            smart.finishLoadMore(1500, true, true);
                        }
                        break;
                    }
                }
                adapter.setContentList(photos);
                smart.finishLoadMore(1000, true, false);
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
//            adapter.notifyDataSetChanged();
        }
        super.onPostExecute(s);
    }
}
