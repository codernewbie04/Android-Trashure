package com.akmalmf.trashure.utils;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.shashank.sony.fancytoastlib.FancyToast;

public class Validate {

    public static boolean cek(EditText et) {
        View focusView = null;
        Boolean cancel=false;
        if (TextUtils.isEmpty(et.getText().toString().trim())) {
            et.setError("Inputan Harus Di Isi");
            focusView = et;
            cancel = true;
        }
        if (cancel) {
            focusView.requestFocus();
        }
        return cancel;
    }

    public static boolean cekEmail(EditText email){
        View focusView = null;
        Boolean cancel=false;
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
            email.setError("Email tidak valid");
            focusView = email;
            cancel = true;
        }
        if (cancel) {
            focusView.requestFocus();
        }
        return cancel;
    }

    public static boolean strlen6Chara(EditText et){
        View focusView = null;
        Boolean cancel=false;
        if (et.getText().toString().length() < 6) {
            et.setError("Data terlalu pendek!");
            focusView = et;
            cancel = true;
        }
        if (cancel) {
            focusView.requestFocus();
        }
        return cancel;
    }

    public static boolean cekNohp(EditText et){
        View focusView = null;
        Boolean cancel=false;
        if (et.getText().toString().length() < 6) {
            et.setError("Nomor HP tidak valid!");
            focusView = et;
            cancel = true;
        }
        if (cancel) {
            focusView.requestFocus();
        }
        return cancel;
    }



    public static boolean cekPassword(EditText et,String Password,String ConfirmPassword){
        View focusView = null;
        Boolean cancel=false;
        if (!Password.equals(ConfirmPassword)) {
            et.setError("Password tidak sama");
            focusView = et;
            cancel = true;
        }
        if (cancel) {
            focusView.requestFocus();
        }
        return cancel;
    }

    public static boolean cekImage(byte[] imageByteArray, Context mContext) {
        if (imageByteArray == null) {
            FancyToast.makeText(mContext, "Image harus di isi!",FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();
            return true;
        } else {
            return false;
        }
    }
}
