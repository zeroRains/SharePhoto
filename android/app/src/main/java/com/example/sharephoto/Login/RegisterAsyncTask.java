package com.example.sharephoto.Login;

import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RegisterAsyncTask extends AsyncTask<String, Void, String> {
    private Context context;

    public RegisterAsyncTask(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {
        String id = strings[0];
        String passwd = strings[1];
        MultipartBody.Builder urlBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        urlBuilder.addFormDataPart("id", id);
        urlBuilder.addFormDataPart("passwd", passwd);

        Request request = new Request.Builder().url(LoginAsyncTask.URL).post(urlBuilder.build()).build();
        try {
            OkHttpClient client = new OkHttpClient();
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String s = response.body().string();
                Gson gson = new Gson();
                LoginResponse response1s = gson.fromJson(s, LoginResponse.class);
                return response1s.getMsg();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
