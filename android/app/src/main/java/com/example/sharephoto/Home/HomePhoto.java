package com.example.sharephoto.Home;

public class HomePhoto {
    private int id;
    private String tag;
    private int icon_id;
    private String username;
    private int start_num;
    private boolean start;
    private String thumbsnail;


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

    public int getIcon_id() {
        return icon_id;
    }

    public void setIcon_id(int icon_id) {
        this.icon_id = icon_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getStart_num() {
        return start_num;
    }

    public void setStart_num(int start_num) {
        this.start_num = start_num;
    }

    public boolean isStart() {
        return start;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public String getThumbsnail() {
        return thumbsnail;
    }

    public void setThumbsnail(String thumbsnail) {
        this.thumbsnail = thumbsnail;
    }
}
