package com.example.sharephoto.Login;

import com.example.sharephoto.Empty;

import java.util.ArrayList;

public class LoginResponse {
    private String msg;
    private ArrayList<Empty> data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ArrayList<Empty> getData() {
        return data;
    }

    public void setData(ArrayList<Empty> data) {
        this.data = data;
    }
}
