package com.example.sharephoto.Detail;

import android.os.AsyncTask;

import com.example.sharephoto.RequestConfig;

import java.io.IOException;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ThumbsupAsyncTask extends AsyncTask<String, Void, String> {
    public static final String URL = RequestConfig.THUMBSUP;

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
                .url(ThumbsupAsyncTask.URL)
                .build();
        try {
            OkHttpClient client = new OkHttpClient();
            client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
