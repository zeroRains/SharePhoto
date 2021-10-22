package com.example.sharephoto.Publication;

import android.graphics.Bitmap;

import com.google.gson.annotations.SerializedName;

public class PublishPhoto {
    private int id;
    @SerializedName("url")
    private String photo_uri;

    public String getPhoto_uri() {
        return photo_uri;
    }

    public void setPhoto_uri(String photo_uri) {
        this.photo_uri = photo_uri;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
