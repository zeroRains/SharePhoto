package com.example.sharephoto.Detail;

import android.app.VoiceInteractor;
import android.os.AsyncTask;

import com.example.sharephoto.RequestConfig;
import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FollowAuthorAsyncTask extends AsyncTask<String, Void, String> {
    public static final String URL = RequestConfig.FOLLOW;


    @Override
    protected String doInBackground(String... strings) {
        String id = strings[0];
        String user = strings[1];
        String author = strings[2];
        String add = strings[3];
        MultipartBody.Builder body = new MultipartBody.Builder().setType(MultipartBody.FORM);
        body.addFormDataPart("id", id)
                .addFormDataPart("user", user)
                .addFormDataPart("author", author)
                .addFormDataPart("add", add);
        Request request = new Request.Builder()
                .post(body.build())
                .url(FollowAuthorAsyncTask.URL)
                .build();

        try {
            OkHttpClient client = new OkHttpClient();
            Response response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
