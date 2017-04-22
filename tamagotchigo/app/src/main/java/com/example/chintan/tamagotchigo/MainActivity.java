package com.example.chintan.tamagotchigo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {
    MyView v;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Disable the title
        requestWindowFeature (Window.FEATURE_NO_TITLE);
        // Make full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        v=null;
        v=new MyView(this);
        setContentView(v);
    }

    @Override
    protected void onPause() {
        if(Assets.mp.isPlaying())
            Assets.mp.pause();
        Assets.isPlaying=false;
        if (!isFinishing()) {
            Assets.isPlaying=false;
            Assets.timeHunger=false;
            if(Assets.state== Assets.GameState.HUNGRY || Assets.state== Assets.GameState.Eating){
                //if(!Assets.is_it_hungry)
                    Assets.soundpools.stop(Assets.hungry_stream);
            }
            // Allow the gator to get hungry in 5 seconds
            float curTime = System.nanoTime() / 1000000000f;
            if(Assets.state== Assets.GameState.EGG) {
                Assets.timeWhenHungry = curTime + 20;
            }else{
                Assets.timeWhenHungry = curTime + 15;
            }
                Intent intent = new Intent(this, GatorService.class);
                intent.setAction(GatorService.ACTION_START);
                startService(intent);
                Assets.noNotify=false;
            try {
                v.pause();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }else{
            Assets.mp.release();
        }
        super.onPause ();
    }

    @Override
    protected void onResume() {
        // Kill the notification, if any
        Assets.isPlaying=true;
        if(Assets.mp!=null){
            Assets.mp.start();
        }
        Intent intent = new Intent(this, GatorService.class);
        intent.setAction(GatorService.ACTION_KILL_NOTIFICATION);
        startService(intent);
        //Assets.timeWhenHungry=2;
        if(v!=null)
            v.resume();
        super.onResume();
    }
}

