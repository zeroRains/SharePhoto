package com.example.sharephoto.Message;

public class Chator {
    private String text;
    private int avatarId;
    private boolean isReceived = true;

    public Chator(String s, int i) {
        this.text = s;
        this.avatarId = i;
    }

    public Chator(String s, int i, boolean isReceived) {
        this.text = s;
        this.avatarId = i;
        this.isReceived = isReceived;
    }

    public boolean isReceived() {
        return isReceived;
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(int avatarId) {
        this.avatarId = avatarId;
    }

    public void setReceived(boolean received) {
        isReceived = received;
    }
}
