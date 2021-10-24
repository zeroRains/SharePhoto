package com.example.sharephoto.Detail;

import android.os.AsyncTask;
import android.util.Log;

import com.example.sharephoto.RequestConfig;

import java.io.IOException;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class StarAsyncTask extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... strings) {
        String id = strings[0];
        String user = strings[1];
        String add = strings[2];
        MultipartBody.Builder body = new MultipartBody.Builder().setType(MultipartBody.FORM);
        body.addFormDataPart("id", id);
        body.addFormDataPart("user", user);
        body.addFormDataPart("add", add);
        Request request = new Request.Builder()
                .url(RequestConfig.FAVOR)
                .post(body.build())
                .build();
        Log.d("request_debug", "doInBackground: "+request.toString());
        OkHttpClient client = new OkHttpClient();
        try {
            client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
