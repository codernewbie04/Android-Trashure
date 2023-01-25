package com.akmalmf.trashure.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.akmalmf.trashure.R;
import com.akmalmf.trashure.utils.PrefManager;

public class SplashAct extends AppCompatActivity {
    Animation app_splash, btt;
    ImageView app_logo;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //Deklarasi
        mContext    =  this;
        app_splash  = AnimationUtils.loadAnimation(this, R.anim.logo_animation);
        app_logo    = findViewById(R.id.applogo);
        //mulai animasi
        app_logo.startAnimation(app_splash);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                    if(!new PrefManager(mContext).isUserLogedOut()){
                        Intent goto_dashboard = new Intent(SplashAct.this, MainAct.class);
                        startActivity(goto_dashboard);
                    } else {
                        Intent goto_getstarted = new Intent(SplashAct.this, MainAct.class);
                        ActivityOptionsCompat option = ActivityOptionsCompat
                                .makeSceneTransitionAnimation(SplashAct.this, app_logo, "applogo_transition");
                        startActivity(goto_getstarted, option.toBundle());
                    }

                    Handler handler2 = new Handler();
                    handler2.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                        }
                    }, 1000);

            }
        }, 2000);
    }
}