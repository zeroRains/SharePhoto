package com.example.sharephoto.Publication;

import android.graphics.Bitmap;

public class PublishPhoto {
    private int id;
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
