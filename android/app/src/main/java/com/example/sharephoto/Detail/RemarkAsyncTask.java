package com.example.sharephoto.Detail;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.sharephoto.R;
import com.example.sharephoto.RequestConfig;
import com.example.sharephoto.Response.BaseResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RemarkAsyncTask extends AsyncTask<String, Void, String> {
    public static final String URL = RequestConfig.GET_COMMENT;
    private Context context;
    private List<Remark> remarks;
    private RemarkAdapter adapter;

    public RemarkAsyncTask(Context context, List<Remark> remarks, RemarkAdapter adapter) {
        this.context = context;
        this.remarks = remarks;
        this.adapter = adapter;
    }

    @Override
    protected String doInBackground(String... strings) {
        String id = strings[0];
        String user = strings[1];
        Request request = new Request.Builder()
                .url(RemarkAsyncTask.URL + "?id=" + id + "&user=" + user)
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

    @Override
    protected void onPostExecute(String s) {
        Gson gson = new Gson();
        Type type = new TypeToken<BaseResponse<List<Remark>>>() {
        }.getType();
        BaseResponse<List<Remark>> response = gson.fromJson(s, type);
        for (Remark remark : response.getData()) {
            remarks.add(0, remark);
        }
        adapter.setRemarks(remarks);
        adapter.notifyDataSetChanged();
        super.onPostExecute(s);
    }
}
