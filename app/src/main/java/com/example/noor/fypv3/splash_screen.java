package com.example.noor.fypv3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import static java.lang.Thread.sleep;

public class splash_screen extends AppCompatActivity {

    ImageView img;
    Intent i;
    Animation anim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);
        img = (ImageView) findViewById(R.id.imglogo);
        anim= AnimationUtils.loadAnimation(this,R.anim.splashtransition);
        img.startAnimation(anim);
        i=new Intent(this,MainActivity.class);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    startActivity(i);
                    finish();
                }
            }
        }).start();
    }
}
