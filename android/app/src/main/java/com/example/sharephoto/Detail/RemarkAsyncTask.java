package com.example.sharephoto.Detail;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.sharephoto.R;
import com.example.sharephoto.RequestConfig;
import com.example.sharephoto.Response.BaseResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RemarkAsyncTask extends AsyncTask<String, Void, String[]> {
    public static final String URL = RequestConfig.GET_COMMENT;
    private Context context;
    private List<Remark> remarks;
    private RemarkAdapter adapter;
    DetailActivity.ViewHolder viewHolder;

    public RemarkAsyncTask(Context context, List<Remark> remarks, RemarkAdapter adapter, DetailActivity.ViewHolder viewHolder) {
        this.context = context;
        this.remarks = remarks;
        this.adapter = adapter;
        this.viewHolder = viewHolder;
    }

    @Override
    protected String[] doInBackground(String... strings) {
        String id = strings[0];
        String user = strings[1];
        Request request = new Request.Builder()
                .url(RemarkAsyncTask.URL + "?id=" + id + "&user=" + user)
                .get()
                .build();
        Request request1 = new Request.Builder()
                .url(RequestConfig.DETAIL + "?id=" + id + "&user=" + user)
                .get()
                .build();
        try {
            OkHttpClient client = new OkHttpClient();
            Response response = client.newCall(request).execute();
            Response response1 = client.newCall(request1).execute();
            if (response.isSuccessful() && response1.isSuccessful()) {
                return new String[]{response.body().string(), response1.body().string(), user};
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String[] strings) {
        String s = strings[0];
        String s1 = strings[1];
        String user = strings[2];
        Gson gson = new Gson();
        // 渲染评论信息
        Type type = new TypeToken<BaseResponse<List<Remark>>>() {
        }.getType();
        BaseResponse<List<Remark>> response = gson.fromJson(s, type);
        for (Remark remark : response.getData()) {
            remarks.add(0, remark);
        }
        adapter.setRemarks(remarks);
        adapter.notifyDataSetChanged();
        // 渲染详细信息
        type = new TypeToken<BaseResponse<List<DetailInfo>>>() {
        }.getType();
        BaseResponse<List<DetailInfo>> res = gson.fromJson(s1, type);
        if (res.getMsg().equals("success")) {
            DetailInfo info = res.getData().get(0);
            Glide.with(context)
                    .load(RequestConfig.URL + info.getIcon())
                    .into(viewHolder.detail_icon);
            viewHolder.detail_username.setText(info.getUsername());
            viewHolder.detail_time.setText(info.getDate());
            if (user.equals(info.getUid())) {
                viewHolder.detail_follow.setVisibility(View.GONE);
            }
            else if (info.getFollow().equals("T")) {
                viewHolder.detail_follow.setSelected(true);
                viewHolder.detail_follow.setTextColor(context.getResources().getColor(R.color.white));
                viewHolder.detail_follow.setText("✓ 已关注");
            } else {
                viewHolder.detail_follow.setSelected(false);
                viewHolder.detail_follow.setTextColor(context.getResources().getColor(R.color.primary));
                viewHolder.detail_follow.setText("+ 关注");
            }
            Glide.with(context)
                    .load(RequestConfig.URL + info.getPhotos()[0])
                    .into(viewHolder.detail_photo);
            viewHolder.detail_zan_status.setSelected(info.getZanStatus().equals("T"));
            viewHolder.detail_zan_num.setText("" + info.getZanNum());
            viewHolder.detail_love_status.setSelected(info.getStarStatus().equals("T"));
            viewHolder.detail_love_num.setText("" + info.getStarNum());
            viewHolder.detail_description_title.setText(info.getTitle());
            viewHolder.detail_description.setText(info.getDescription());
        }
        super.onPostExecute(strings);
    }
}
