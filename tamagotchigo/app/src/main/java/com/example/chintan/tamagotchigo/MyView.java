package com.example.chintan.tamagotchigo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import com.example.chintan.tamagotchigo.Assets;
import com.example.chintan.tamagotchigo.MainThread;

import java.util.Locale;

public class MyView extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder holder = null;
     static Context context;
    Context mCtxt;
    public static MainThread t = null,t1=null;
    //Try
    final Object lock = new Object();

    // Constructor
    @SuppressWarnings("deprecation")
    public MyView (Context context) {
        super(context);
        this.context = context;
        holder = getHolder();
        this.context = context;
        holder.addCallback(this);

        //Load Media Player
        Assets.mp = MediaPlayer.create(context,R.raw.background_music);
        Assets.mp.start();
        Assets.mp.setLooping(true);
        // Load the sound effects
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            Assets.soundpools = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        }
        else {
            AudioAttributes attributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();
            Assets.soundpools = new SoundPool.Builder()
                    .setAudioAttributes(attributes)
                    .build();

        }
        //Assets.mediaPlayer= MediaPlayer.create(context,R.raw.background_music);
        Assets.sound_EggCrack = Assets.soundpools.load(context, R.raw.egg_crack, 2);
        Assets.sound_happy = Assets.soundpools.load(context, R.raw.happy_duck_, 1);
        Assets.sound_hungry = Assets.soundpools.load(context, R.raw.hungry_duck, 1);
    }

    public void pause () throws InterruptedException {
        System.out.println("Main Activity Mai thread Pause Method");
        t.setRunning(false);
        while (true) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            break;
        }
        t = null;
    }

    public void resume ()
    {
        Assets.mp.start();
        t= new MainThread(holder,context);
        t.setRunning(true);
        t.start();
        System.out.println("Main Activity Mai thread Resume Method");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        double x, y;
        int action = event.getAction();
        x = event.getX();
        y = event.getY();
        //t.setXY ((int)x, (int)y);
        System.out.println("Coord:"+ x +"....y:"+y);

        //Handling preference activity
        float Prefx=Assets.Prefx;
        float Prefy = 100;
        mCtxt =context;//for prefrence activity context

        //float center=(float) Math.sqrt((x-Prefx)*(x-Prefx)+(y-Prefy)*(y-Prefy));
        float Prefx1 = Prefx + Assets.prefs.getWidth()-20;
        float Prefy1 = Prefy + Assets.prefs.getHeight()-10;
        if ((x > Prefx && x< Prefx1) && (y > Prefy && y < Prefy1)) {
            mCtxt = getContext();
            Intent intent = new Intent(mCtxt, PrefsActivity.class);
            mCtxt.startActivity(intent);
            ///////////////////////         TTS         //////////////////////////////////
            if(Assets.isTTS) {
                if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    Assets.t1.speak("Opening Settings", TextToSpeech.QUEUE_FLUSH, null);
                } else {
                    Assets.t1.speak("Opening Settings", TextToSpeech.QUEUE_FLUSH, null, "1");
                }
            }
            /////////////////////////////////////////////////////////////////////////
            //Toast.makeText(context, "Want Preferences??", Toast.LENGTH_SHORT).show();
        }

        //Handling Food Button
        Prefx=100;
        Prefy = 100;
        Prefx1 = Prefx + Assets.food1.getWidth()-20;
        Prefy1 = Prefy + Assets.food1.getHeight()-10;
        if ((x > Prefx && x< Prefx1) && (y > Prefy && y < Prefy1)) {
            if(Assets.is_it_in_hungrystate) {
                Assets.is_hungry = true;
                ///////////////////////         TTS         //////////////////////////////////
                if(Assets.isTTS) {
                    if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                        Assets.t1.speak("Thank you for food", TextToSpeech.QUEUE_FLUSH, null);
                    } else {
                        Assets.t1.speak("Thank you for food", TextToSpeech.QUEUE_FLUSH, null, "1");
                    }
                }
                /////////////////////////////////////////////////////////////////////////
                //Toast.makeText(context, "Thank you for Food!", Toast.LENGTH_SHORT).show();

                if(Assets.state== Assets.GameState.HAPPY){
                    float curTime= System.nanoTime() / 1000000000f;
                    Assets.TotalEatingTime+= Assets.GAMETIMER-curTime;
                    if(Assets.TotalEatingTime<120)
                        Assets.GAMETIMER =System.nanoTime() / 1000000000f;
                    else
                        Assets.HappyStateTimer=20;
                }
            }
            else{
                if(Assets.isTTS) {
                    ///////////////////////////////////         TTS         //////////////////////////////////
                    if(Assets.isTTS) {
                        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                            Assets.t1.speak("No, I am not hungry", TextToSpeech.QUEUE_FLUSH, null);
                        } else {
                            Assets.t1.speak("No, I am not hungry", TextToSpeech.QUEUE_FLUSH, null, "1");
                        }
                    }
                    /////////////////////////////////////////////////////////////////////////////////////////
                }

            }
            //Toast.makeText(context, "No, I'm Not Hungry Now!", Toast.LENGTH_SHORT).show();
        }

        return true;
    }

    @Override
    public void surfaceCreated (SurfaceHolder holder) {
        // Create and start a drawing thread whose Runnable object is defined by this class (MainView)
        if (t == null) {
            t = new MainThread(holder, context);
            t.setRunning(true);
            t.start();
            setFocusable(true); // make sure we get events
        }
    }
    // Neither of these two methods are used in this example, however, their definitions are required because SurfaceHolder.Callback was implemented
    @Override public void surfaceChanged(SurfaceHolder sh, int f, int w, int h) {}
    @Override public void surfaceDestroyed(SurfaceHolder sh) { }
}
