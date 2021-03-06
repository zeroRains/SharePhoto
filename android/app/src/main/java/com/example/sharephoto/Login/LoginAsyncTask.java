package com.example.sharephoto.Login;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.sharephoto.Response.EmptyResponse;
import com.example.sharephoto.MainActivity;
import com.example.sharephoto.RequestConfig;
import com.google.gson.Gson;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginAsyncTask extends AsyncTask<String, Void, String> {
    private Context context;
    public final static String URL = RequestConfig.LOGIN;
    private String username;

    public LoginAsyncTask(Context context, String username) {
        this.context = context;
        this.username = username;
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
                assert response.body() != null;
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
//            Log.d("zerorains", "onPostExecute: "+s);
            if (s.equals("success")) {
                Toast.makeText(context, "???????????????????????????", Toast.LENGTH_SHORT).show();
                @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = context.getSharedPreferences("data", Context.MODE_PRIVATE).edit();
                editor.putString("username", username);
                editor.apply();
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
                ((Activity) context).finish();
            } else {
                Toast.makeText(context, "???????????????????????????????????????", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(context, "??????????????????", Toast.LENGTH_SHORT).show();
        }
        super.onPostExecute(s);
    }
}
