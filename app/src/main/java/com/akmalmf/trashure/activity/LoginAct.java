package com.akmalmf.trashure.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.akmalmf.trashure.R;
import com.akmalmf.trashure.json.ResponseAuth;
import com.akmalmf.trashure.utils.PrefManager;
import com.akmalmf.trashure.utils.Validate;
import com.akmalmf.trashure.utils.api.ServiceGenerator;
import com.akmalmf.trashure.utils.api.request;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.akmalmf.trashure.constants.Constants.AUTH_PASS;
import static com.akmalmf.trashure.constants.Constants.AUTH_USER;

public class LoginAct extends AppCompatActivity implements View.OnClickListener {
    Button btn_masuk, btn_google, btn_facebook;
    EditText et_email, et_password;
    TextView lupa_pass, daftar;
    RelativeLayout rlprogress;
    ImageButton show_password;
    Boolean is_show = false;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        startInit();
    }

    private void startInit() {
        initUI();
        initEvent();
    }

    private void initUI() {
        mContext = this;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(mContext, R.color.transparent));
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        btn_masuk = findViewById(R.id.btn_masuk);
        btn_google = findViewById(R.id.btn_google);
        btn_facebook = findViewById(R.id.btn_facebook);

        et_password = findViewById(R.id.et_password);
        et_email = findViewById(R.id.et_email);

        lupa_pass = findViewById(R.id.lupa_pass);
        daftar = findViewById(R.id.daftar);

        show_password = findViewById(R.id.show_password);

        rlprogress = findViewById(R.id.rlprogress);
    }

    private void initEvent() {
        daftar.setOnClickListener(this);
        lupa_pass.setOnClickListener(this);
        btn_masuk.setOnClickListener(this);
        show_password.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_masuk:
                if(validate_login())
                    login();
                break;
            case R.id.show_password:
                if(!is_show){
                    et_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    is_show = true;
                    show_password.setColorFilter(ContextCompat.getColor(mContext, R.color.greenPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
                } else {
                    show_password.setColorFilter(ContextCompat.getColor(mContext, R.color.gray), android.graphics.PorterDuff.Mode.SRC_IN);
                    et_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    is_show = false;
                }
                break;
            case R.id.daftar:
                Intent goto_daftar = new Intent(mContext, DaftarAct.class);
                startActivity(goto_daftar);
                break;
            case R.id.lupa_pass:
                Intent goto_forget = new Intent(mContext, LupaPassAct.class);
                startActivity(goto_forget);
                break;

        }
    }

    private boolean validate_login() {
        return (!Validate.cek(et_email)&&!Validate.cek(et_password)&&!Validate.cekEmail(et_email));
    }

    private void login() {
        rlprogress.setVisibility(View.VISIBLE);
        request service = ServiceGenerator.createService(request.class, AUTH_USER, AUTH_PASS, null);
        service.login(et_email.getText().toString(), et_password.getText().toString()).enqueue(new Callback<ResponseAuth>() {
            @Override
            public void onResponse(Call<ResponseAuth> call, Response<ResponseAuth> response) {
                rlprogress.setVisibility(View.GONE);
                if (response.code() == 200) {
                    new PrefManager(mContext).saveUserData(response.body().getData());
                    Intent goto_dashboard = new Intent(mContext, MainAct.class);
                    startActivity(goto_dashboard);
                    finishAffinity();
                } else {
                    Gson gson = new GsonBuilder().create();
                    ResponseAuth mError = new ResponseAuth();
                    try {
                        mError= gson.fromJson(response.errorBody().string(), ResponseAuth.class);
                        FancyToast.makeText(mContext,mError.getMsg(),FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();
                    } catch (IOException e) {
                        Toast.makeText(mContext, "Invalid JSON", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseAuth> call, Throwable t) {
                rlprogress.setVisibility(View.GONE);
                FancyToast.makeText(mContext, "No Connection!",FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();
            }
        });
    }
}