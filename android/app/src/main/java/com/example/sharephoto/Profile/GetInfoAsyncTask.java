package com.example.sharephoto.Profile;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import com.bumptech.glide.Glide;
import com.example.sharephoto.RequestConfig;
import com.example.sharephoto.Response.BaseResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetInfoAsyncTask extends AsyncTask<String, Void, String> {
    private ProfileFragment.ViewHolder vh;
    public static final String URL = RequestConfig.SHOW_USER;
    private Context context;

    public GetInfoAsyncTask(Context context, ProfileFragment.ViewHolder vh) {
        this.vh = vh;
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {
        String id = strings[0];
        Request request = new Request.Builder()
                .url(GetInfoAsyncTask.URL + "?id=" + id)
                .get()
                .build();
        try {
            OkHttpClient client = new OkHttpClient();
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onPostExecute(String s) {
        Gson gson = new Gson();
        Type type = new TypeToken<BaseResponse<List<ProfileInfoResponse>>>() {
        }.getType();
        BaseResponse<List<ProfileInfoResponse>> response = gson.fromJson(s, type);
        if (response.getMsg().equals("success")) {
            ProfileInfoResponse info = response.getData().get(0);
            vh.profile_user_name.setText(info.getUsername());
            Glide.with(context).load(RequestConfig.URL + info.getUrl()).into(vh.profile_avatar_icon);
            vh.profile_thumb_sub_number.setText("" + info.getGreat());
            vh.profile_fan_number.setText("" + info.getFan());
            vh.profile_subscription_number.setText("" + info.getStart());
            vh.profile_sex.setSelected(info.getSex().equals("F"));
        }
        super.onPostExecute(s);
    }
}
