package com.example.sharephoto;

import com.google.gson.annotations.SerializedName;

public class BaseResponse<T> {
    private String msg;
    public final static int RESPONSE_SUCCESS = 0;
    @SerializedName("data")
    private T data;

    public BaseResponse() {

    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
