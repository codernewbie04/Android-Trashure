package com.akmalmf.trashure.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.akmalmf.trashure.models.DataUser;

public class PrefManager {

    Context context;

    public PrefManager(Context context) {
        this.context = context;
    }

    public void saveUserData(DataUser du) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("userid",du.getId());
        editor.putString("nama",du.getNama());
        editor.putString("email",du.getEmail());
        editor.putString("nohp",du.getNohp());
        editor.putString("level",du.getLevel());
        editor.putString("foto",du.getFoto());
        editor.putString("saldo",du.getSaldo());
        editor.putString("apikey",du.getApikey());
        editor.commit();
    }

    public  int getUserId(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("userid", 0);
    }

    public  String getFoto(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
        return sharedPreferences.getString("foto", "");
    }

    public  String getNama(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
        return sharedPreferences.getString("nama", "");
    }

    public  String getEmail(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
        return sharedPreferences.getString("email", "");
    }

    public  String getNohp(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
        return sharedPreferences.getString("nohp", "");
    }

    public  String getLevel(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
        return sharedPreferences.getString("level", "");
    }

    public String getSaldo(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
        return sharedPreferences.getString("saldo", "");
    }

    public  String getApikey(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
        return sharedPreferences.getString("apikey", "");
    }

    public boolean isUserLogedOut() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
        boolean isUsernameEmpty = sharedPreferences.getString("email", "").isEmpty();
        boolean isTokenEmpty = sharedPreferences.getString("apikey", "").isEmpty();
        return isUsernameEmpty || isTokenEmpty;
    }
}
