package com.akmalmf.trashure.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataUser {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("nama")
    @Expose
    private String nama;

    @SerializedName("nohp")
    @Expose
    private String nohp;

    @SerializedName("saldo")
    @Expose
    private String saldo;

    @SerializedName("foto")
    @Expose
    private String foto;

    @SerializedName("level")
    @Expose
    private String level;

    @SerializedName("apikey")
    @Expose
    private String apikey;

    public String getFoto() {
        return foto;
    }

    public String getEmail() {
        return email;
    }

    public String getApikey() {
        return apikey;
    }

    public int getId() {
        return id;
    }

    public String getSaldo() {
        return saldo;
    }

    public String getLevel() {
        return level;
    }

    public String getNama() {
        return nama;
    }

    public String getNohp() {
        return nohp;
    }
}
