package com.example.sharephoto.Home;

import com.google.gson.annotations.SerializedName;

public class HomePhoto {
    @SerializedName("id")
    private int id;
    @SerializedName("title")
    private String tag;
    @SerializedName("iconId")
    private String iconId;
    @SerializedName("author")
    private String username;
    @SerializedName("starNum")
    private int starNum;
    @SerializedName("star")
    private String star;
    @SerializedName("thumbnail")
    private String thumbSnail;

    public String getStar() {
        return star;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getIconId() {
        return iconId;
    }

    public void setIconId(String iconId) {
        this.iconId = iconId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getStarNum() {
        return starNum;
    }

    public void setStarNum(int starNum) {
        this.starNum = starNum;
    }

    public String isStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public String getThumbSnail() {
        return thumbSnail;
    }

    public void setThumbSnail(String thumbSnail) {
        this.thumbSnail = thumbSnail;
    }
}
