package com.example.sharephoto.Home;

public class HomePhoto {
    private int id;
    private String tag;
    private int iconId;
    private String username;
    private int starNum;
    private boolean star;
    private String thumbSnail;


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

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
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

    public boolean isStar() {
        return star;
    }

    public void setStar(boolean star) {
        this.star = star;
    }

    public String getThumbSnail() {
        return thumbSnail;
    }

    public void setThumbSnail(String thumbSnail) {
        this.thumbSnail = thumbSnail;
    }
}
