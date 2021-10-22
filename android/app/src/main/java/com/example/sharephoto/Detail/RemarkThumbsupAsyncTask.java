package com.example.sharephoto.Detail;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.sharephoto.R;
import com.example.sharephoto.RequestConfig;
import com.example.sharephoto.Response.BaseResponse;
import com.example.sharephoto.Response.Empty;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RemarkThumbsupAsyncTask extends AsyncTask<String, Void, String> {
    public static final String URL = RequestConfig.COMMENT_THUMBSUP;
    Context context;

    public RemarkThumbsupAsyncTask(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {
        String id = strings[0];
        String user = strings[1];
        String add = strings[2];
        MultipartBody.Builder body = new MultipartBody.Builder().setType(MultipartBody.FORM);
        body.addFormDataPart("id", id)
                .addFormDataPart("user", user)
                .addFormDataPart("add", add);
        Request request = new Request.Builder()
                .post(body.build())
                .url(RemarkThumbsupAsyncTask.URL)
                .build();
        try {
            OkHttpClient client = new OkHttpClient();
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                assert response.body() != null;
                return response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        if (s != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<BaseResponse<List<Empty>>>() {
            }.getType();
            BaseResponse<List<Empty>> msg = gson.fromJson(s, type);
//            if (s.equals("true")) {
//                Toast.makeText(context, "点赞成功", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(context, "取消点赞", Toast.LENGTH_SHORT).show();
//            }
        }
        super.onPostExecute(s);
    }
}
