package com.example.sharephoto.Home;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.sharephoto.Detail.RemarkAsyncTask;
import com.example.sharephoto.RequestConfig;

import java.io.IOException;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DeleteAsyncTask extends AsyncTask<String,Void,String> {
    public static final String URL = RequestConfig.SHUOSHUO_DELETE;
    private Context context;

    public DeleteAsyncTask(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {
        String id = strings[0];
        MultipartBody.Builder body = new MultipartBody.Builder().setType(MultipartBody.FORM);
        body.addFormDataPart("id",id);
        Request request = new Request.Builder()
                .url(URL)
                .post(body.build())
                .build();
        Log.d("zerorains", "doInBackground: "+URL);
        try{
            OkHttpClient client = new OkHttpClient();
            Response response = client.newCall(request).execute();
            if(response.isSuccessful()){
                return "true";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return request.body().toString();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        Toast.makeText(context,"删除成功",Toast.LENGTH_SHORT).show();
        ((Activity)context).finish();
    }
}
