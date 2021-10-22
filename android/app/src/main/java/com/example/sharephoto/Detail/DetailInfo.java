package com.example.sharephoto.Detail;

public class DetailInfo {
    private String date;
    private String description;
    private String follow;
    private String icon;
    private String[] photos;
    private int starNum;
    private String starStatus;
    private String title;
    private String username;
    private int zanNum;
    private String zanStatus;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    private String uid;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFollow() {
        return follow;
    }

    public void setFollow(String follow) {
        this.follow = follow;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String[] getPhotos() {
        return photos;
    }

    public void setPhotos(String[] photos) {
        this.photos = photos;
    }

    public int getStarNum() {
        return starNum;
    }

    public void setStarNum(int starNum) {
        this.starNum = starNum;
    }

    public String getStarStatus() {
        return starStatus;
    }

    public void setStarStatus(String starStatus) {
        this.starStatus = starStatus;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getZanNum() {
        return zanNum;
    }

    public void setZanNum(int zanNum) {
        this.zanNum = zanNum;
    }

    public String getZanStatus() {
        return zanStatus;
    }

    public void setZanStatus(String zanStatus) {
        this.zanStatus = zanStatus;
    }
}
