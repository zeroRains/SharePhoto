package com.example.sharephoto.Publication;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.sharephoto.RequestConfig;
import com.example.sharephoto.Response.BaseResponse;
import com.example.sharephoto.Response.Empty;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PublishSubmitAsyncTask extends AsyncTask<String, Void, String> {
    public static String URL = RequestConfig.PUBLISH;
    private Context context;

    public PublishSubmitAsyncTask(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {
        String id = strings[0];
        String photo = strings[1];
        String title = strings[2];
        String description = strings[3];
        String topic = strings[4];
        String category = strings[5];
        MultipartBody.Builder body = new MultipartBody.Builder().setType(MultipartBody.FORM);
        body.addFormDataPart("id", id)
                .addFormDataPart("photo", photo)
                .addFormDataPart("title", title)
                .addFormDataPart("description", description)
                .addFormDataPart("topic", topic)
                .addFormDataPart("category", category);
        Request request = new Request.Builder()
                .url(PublishSubmitAsyncTask.URL)
                .post(body.build())
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

    @Override
    protected void onPostExecute(String s) {
        if (s == null) {
            Toast.makeText(context, "发布失败，请检查网络和内容", Toast.LENGTH_SHORT).show();
        } else {
            Gson gson = new Gson();
            Type type = new TypeToken<BaseResponse<List<Empty>>>() {
            }.getType();
            BaseResponse<List<Empty>> response = gson.fromJson(s, type);
            if (response.getMsg().equals("success")) {
                Toast.makeText(context, "发布成功", Toast.LENGTH_SHORT).show();
                ((Activity) context).finish();
            } else {
                Toast.makeText(context, "发布失败，请检查网络和内容", Toast.LENGTH_SHORT).show();
            }
        }
        super.onPostExecute(s);
    }
}
