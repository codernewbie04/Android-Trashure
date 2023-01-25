package com.akmalmf.trashure.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.akmalmf.trashure.R;
import com.akmalmf.trashure.json.ResponseAuth;
import com.akmalmf.trashure.json.ResponseForget;
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

public class LupaPassAct extends AppCompatActivity implements View.OnClickListener {
    RelativeLayout rlprogress;
    Button btn_forget;
    EditText et_email;
    Context mContext;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lupa_pass);
        startInit();
    }

    private void startInit() {
        initUI();
        initEvent();
    }

    private void initUI() {
        mContext    = this;
        rlprogress  = findViewById(R.id.rlprogress);
        btn_forget  = findViewById(R.id.btn_forget);
        et_email    = findViewById(R.id.et_email);
        toolbar     = findViewById(R.id.toolbar);
    }

    private void initEvent() {
        btn_forget.setOnClickListener(this);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_forget:
                if(validate_foreget())
                    forget_password();
                break;
        }
    }

    private boolean validate_foreget() {
        return !Validate.cek(et_email) && !Validate.cekEmail(et_email);
    }

    private void forget_password() {
        rlprogress.setVisibility(View.VISIBLE);
        request service = ServiceGenerator.createService(request.class, AUTH_USER, AUTH_PASS, null);
        service.forget(et_email.getText().toString()).enqueue(new Callback<ResponseForget>() {
            @Override
            public void onResponse(Call<ResponseForget> call, Response<ResponseForget> response) {
                rlprogress.setVisibility(View.GONE);
                if (response.code() == 200) {
                    FancyToast.makeText(mContext, response.body().getMsg(),FancyToast.LENGTH_LONG,FancyToast.SUCCESS,false).show();
                    finish();
                } else {
                    Gson gson = new GsonBuilder().create();
                    ResponseForget mError = new ResponseForget();
                    try {
                        mError= gson.fromJson(response.errorBody().string(), ResponseForget.class);
                        FancyToast.makeText(mContext,mError.getMsg(),FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();
                    } catch (IOException e) {
                        Toast.makeText(mContext, "Invalid JSON", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseForget> call, Throwable t) {
                rlprogress.setVisibility(View.GONE);
                FancyToast.makeText(mContext, "No Connection!",FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();
            }
        });
    }
}