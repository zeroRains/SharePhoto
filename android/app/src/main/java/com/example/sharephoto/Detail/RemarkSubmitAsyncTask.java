package com.example.sharephoto.Detail;

import android.content.Context;
import android.os.AsyncTask;
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

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RemarkSubmitAsyncTask extends AsyncTask<String, Void, String> {
    Context context;

    public RemarkSubmitAsyncTask(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {
        String id = strings[0];
        String user = strings[1];
        String content = strings[2];
        MultipartBody.Builder body = new MultipartBody.Builder().setType(MultipartBody.FORM);
        body.addFormDataPart("id", id)
                .addFormDataPart("user", user)
                .addFormDataPart("content", content);
        Request request = new Request.Builder()
                .url(RequestConfig.PUBLISH_COMMENT)
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
        Gson gson = new Gson();
        Type type = new TypeToken<BaseResponse<List<Empty>>>() {
        }.getType();
        BaseResponse<List<Empty>> res = gson.fromJson(s, type);
        if (res.getMsg().equals("success")) {
            Toast.makeText(context, "发表成功！", Toast.LENGTH_SHORT).show();
        }
        super.onPostExecute(s);
    }
}
