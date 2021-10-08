package com.samarthappwork.superbazaar.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Pair;
import android.view.View;
import android.widget.Toast;

import com.samarthappwork.superbazaar.R;
import com.samarthappwork.superbazaar.last_acivity_data;

public class Activity_splash extends AppCompatActivity {

    final  static  int SPLASH_SCREEN = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        new Handler(Looper.myLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                last_acivity_data database =  new last_acivity_data(Activity_splash.this);
                Intent intent ;
                if(database.islogined() == 1) {

                    intent = new Intent(Activity_splash.this,MainActivity.class);
                }
                else{
                    intent = new Intent(Activity_splash.this,activity_login.class);
                    database.setuser();

                }
                //TODO : MAKE ANIMATION WORK

//                Pair[] pairs = new Pair[2];
//                pairs[0]=new Pair<View,String>(splash_image,"image");
//                pairs[1]=new Pair<View,String>(splash_text,"text");

                // ActivityOptions options =ActivityOptions.makeSceneTransitionAnimation(splash_activity.this,pairs);

                startActivity(intent);
                finish();
            }
        },SPLASH_SCREEN);
    }

    @Override
    public void onBackPressed() {
    }
}