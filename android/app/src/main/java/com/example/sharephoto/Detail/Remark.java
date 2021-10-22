package com.example.sharephoto.Detail;

import com.google.gson.annotations.SerializedName;

public class Remark {
    @SerializedName("commentId")
    private String commentId;
    @SerializedName("iconId")
    private String icon;
    @SerializedName("username")
    private String username;
    @SerializedName("thumbsupNum")
    private int num;
    @SerializedName("content")
    private String content;
    @SerializedName("date")
    private String date;
    @SerializedName("isThumbsup")
    private String status;

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getStatus() {
        return status;
    }


    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String isStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
