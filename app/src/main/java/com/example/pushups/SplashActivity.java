package com.example.pushups;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

public class SplashActivity extends AppCompatActivity {
    private TextView appName;
    CharSequence charSequence;
    int index;
    long delay = 200;
    Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();

        appName = findViewById(R.id.app_name);
        Typeface typeface = ResourcesCompat.getFont(this, R.font.spacequest); //font 'blacklist' za ime app
        appName.setTypeface(typeface);

        Animation anim = AnimationUtils.loadAnimation(this, R.anim.animacija);
        appName.setAnimation(anim);



        //thread ce nakon 3.5sek preko intenta pokrenuti mainactivity
        new Thread() {
            @Override
            public void run () {
                try {
                    sleep(3500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //prebaci na mainActivity
                Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                startActivity(intent);
                SplashActivity.this.finish(); //close splashActivity
            }
        }.start();
    }
}
