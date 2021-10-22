package com.example.sharephoto.Publication;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.sharephoto.RequestConfig;
import com.example.sharephoto.Response.BaseResponse;
import com.example.sharephoto.Response.EmptyResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PublishAsyncTask extends AsyncTask<String, Void, String> {
    private Context context;
    PublishPhotoAdapter adapter;
    List<PublishPhoto> photos;
    public static String URL = RequestConfig.UPLOAD_IMAGES;

    public PublishAsyncTask(Context context, PublishPhotoAdapter adapter, List<PublishPhoto> photos) {
        this.context = context;
        this.adapter = adapter;
        this.photos = photos;
    }

    @Override
    protected String doInBackground(String... strings) {
        String path = strings[0];
        File file = new File(path);
        RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("file", file.getName(), fileBody)
                .build();
        Request request = new Request.Builder()
                .url(PublishAsyncTask.URL)
                .post(requestBody)
                .build();
        try {
            OkHttpClient client = new OkHttpClient();
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                Log.d("zerorains", "doInBackground: " + response.isSuccessful());
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
        String msg = gson.fromJson(s, EmptyResponse.class).getMsg();
        if (msg.equals("success")) {
            Type type = new TypeToken<BaseResponse<List<PublishPhoto>>>() {
            }.getType();
            BaseResponse<List<PublishPhoto>> response = gson.fromJson(s, type);
            for (PublishPhoto photo : response.getData()) {
                photos.add(photo);
                adapter.setPhotos(photos);
            }
            Toast.makeText(context, "上传成功！", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "上传失败，请检查网络状态!", Toast.LENGTH_SHORT).show();
        }
        super.onPostExecute(s);
    }
}
