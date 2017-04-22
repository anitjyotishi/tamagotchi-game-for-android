package com.example.chintan.tamagotchigo;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by chintan24 on 7/25/2016.
 */
public class SplashActivity extends Activity {
    private static int SPLASH_TIME_OUT = 3000;
    private static boolean quit;
    @Override
    protected void onCreate(Bundle inBundle) {
        super.onCreate(inBundle);
        // Disable the title
        requestWindowFeature (Window.FEATURE_NO_TITLE);
        // Make full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        quit =false;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(!quit) {
                    Intent i = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        }, SPLASH_TIME_OUT);
    }

    @Override
    public void onBackPressed() {
        quit=true;
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        if(!Assets.noNotify) {
            Assets.state = Assets.GameState.HUNGRY;
            Assets.is_it_hungry = true;
            Assets.isPlaying=true;
        }
        if(Assets.mp!=null)
            Assets.mp.start();
        else {
            Assets.mp = MediaPlayer.create(this, R.raw.background_music);
            Assets.mp.start();
        }
        // if(!Assets.isPlaying)
            //Assets.soundpool.play(Assets.sound_hungry, 1, 1, 1, -1, 1);
        super.onResume();
    }
}


