package com.example.sharephoto.Profile;

class Profile {
    private int Imageid;
    private String category;
    private String tag;
    private String username;
    private String thumbsnail;

    Profile() {
    }

    public int getImageid() {
        return Imageid;
    }

    public void setImageid(int id) {
        this.Imageid = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getThumbsnail() {
        return thumbsnail;
    }

    public void setThumbsnail(String thumbsnail) {
        this.thumbsnail = thumbsnail;
    }
}
