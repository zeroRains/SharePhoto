package com.example.sharephoto.Login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.sharephoto.Response.EmptyResponse;
import com.example.sharephoto.MainActivity;
import com.example.sharephoto.RequestConfig;
import com.google.gson.Gson;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RegisterAsyncTask extends AsyncTask<String, Void, String> {
    private Context context;
    public static String URL = RequestConfig.REGISTER;

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

        Request request = new Request.Builder().url(RegisterAsyncTask.URL).post(urlBuilder.build()).build();
        try {
            OkHttpClient client = new OkHttpClient();
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String s = response.body().string();
                Gson gson = new Gson();
                EmptyResponse response1s = gson.fromJson(s, EmptyResponse.class);
                return response1s.getMsg();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        if (s != null) {
            Log.d("zerorains", "onPostExecute: " + s);
            if (s.equals("success")) {
                Toast.makeText(context, "注册成功，欢迎您！", Toast.LENGTH_SHORT).show();
                ((Activity) context).finish();
            } else {
                Toast.makeText(context, "账户已经存在", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(context, "网络请求失败", Toast.LENGTH_SHORT).show();
        }
        super.onPostExecute(s);
    }
}
