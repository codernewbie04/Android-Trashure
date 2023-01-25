package com.akmalmf.trashure.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseForget {
    @SerializedName("status")
    @Expose
    private int status;

    @SerializedName("msg")
    @Expose
    private String msg;

    public String getMsg() {
        return msg;
    }

    public int getStatus() {
        return status;
    }
}
