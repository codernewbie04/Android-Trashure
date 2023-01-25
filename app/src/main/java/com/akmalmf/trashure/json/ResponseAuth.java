package com.akmalmf.trashure.json;

import com.akmalmf.trashure.models.DataUser;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseAuth {
    @SerializedName("status")
    @Expose
    private int status;

    @SerializedName("msg")
    @Expose
    private String msg;

    @SerializedName("data")
    @Expose
    private DataUser data = null;

    public DataUser getData() {
        return data;
    }

    public int getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }
}
