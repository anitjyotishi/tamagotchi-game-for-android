package com.example.chintan.tamagotchigo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.view.SurfaceHolder;

import java.util.Locale;

public class MainThread extends Thread {
    private SurfaceHolder holder;
    private Handler handler;        // required for running code in the UI thread
    private boolean isRunning = false;
    //int odd=1;
    tama t;
    tamaEat t1;
    Context context;
    Paint paint;
    Canvas canvas;
    int counter=0;
    int touchx, touchy;    // x,y of touch event
    boolean touched;    // true if touch happened

    private static final Object lock = new Object();

    static int score = 0;

    public MainThread(SurfaceHolder surfaceHolder, Context context) {
        holder = surfaceHolder;
        this.context = context;
        handler = new Handler();
        touched = false;
        //Loading TTs
        if(MyView.context!=null) {
            try {
                Assets.t1 = new TextToSpeech(MyView.context, new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int status) {
                        if (status != TextToSpeech.ERROR) {
                            Assets.t1.setLanguage(Locale.UK);
                        }
                    }
                });
            } catch (Exception e) {
                System.out.println(e);
            }
        }

       // Assets.state = Assets.GameState.EGG;
    }

    public void setRunning(boolean b) {
        isRunning = b;    // no need to synchronize this since this is the only line of code to writes this variable
    }

    @Override
    public void run() {
        while (isRunning) {
            // Lock the canvas before drawing
            if(!holder.getSurface().isValid())
                continue;
            System.out.println("xyzMain Activity Resume Method");
            Canvas canvas = holder.lockCanvas();
            if (canvas != null) {
                this.canvas =canvas;
                // Perform drawing operations on the canvas
                render(canvas);
                // After drawing, unlock the canvas and display it
                holder.unlockCanvasAndPost(canvas);
            }
        }
    }

    //Touch for preference
    public void setXY(int x, int y) {
        synchronized (lock) {
            this.touched = true;
            touchx = x;
            touchy = y;
        }
    }

    // Loads graphics, etc. used in game
    private void loadData(Canvas canvas) {
        Bitmap bmp;
        int newWidth = 0, newHeight = 0;
        float scaleFactor = 0.0f;

        // Create a paint object for drawing vector graphics
        paint = new Paint();

        // Load food bar
        bmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.egg);
        // Compute size of bitmap needed (suppose want height = 10% of screen height)
        newHeight = (int) (canvas.getHeight() * 0.1f);
        // Scale it to a new size
        Assets.egg = Bitmap.createScaledBitmap(bmp, canvas.getWidth(), newHeight, false);
        // Delete the original
        bmp = null;

        loadButtons(bmp, newWidth, newHeight, scaleFactor, canvas);
        loadBugs(bmp, newWidth, newHeight, scaleFactor, canvas);
        //loadBugs(bmp, newWidth, newHeight, scaleFactor, canvas);

    }
    public static boolean setXY(int x, int y, int rad){
        return true;
    }

    private void loadButtons(Bitmap bmp, int newWidth, int newHeight, float scaleFactor, Canvas canvas) {
        bmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.prefs);
        // Compute size of bitmap needed (suppose want width = 20% of screen width)
        newWidth = (int) (canvas.getWidth() * 0.2f);
        // What was the scaling factor to get to this?
        scaleFactor = (float) newWidth / bmp.getWidth();
        // Compute the new height
        newHeight = (int) (bmp.getHeight() * scaleFactor);
        // Scale it to a new size
        Assets.prefs = Bitmap.createScaledBitmap(bmp, newWidth, newHeight, false);
        // Delete the original
        bmp = null;
    }

    private void loadBugs(Bitmap bmp, int newWidth, int newHeight, float scaleFactor, Canvas canvas) {

        // Load the Food Button
        bmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.food);
        // Compute size of bitmap needed (suppose want width = 20% of screen width)
        newWidth = (int) (canvas.getWidth() * 0.2f);
        // What was the scaling factor to get to this?
        scaleFactor = (float) newWidth / bmp.getWidth();
        // Compute the new height
        newHeight = (int) (bmp.getHeight() * scaleFactor);
        // Scale it to a new size
        Assets.food1 = Bitmap.createScaledBitmap(bmp, newWidth, newHeight, false);
        // Delete the original
        bmp = null;

        // Load the Food Button
        bmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.eggfirst);
        // Compute size of bitmap needed (suppose want width = 20% of screen width)
        newWidth = (int) (canvas.getWidth() * 0.2f);
        // What was the scaling factor to get to this?
        scaleFactor = (float) newWidth / bmp.getWidth();
        // Compute the new height
        newHeight = (int) (bmp.getHeight() * scaleFactor);
        // Scale it to a new size
        Assets.egg1 = Bitmap.createScaledBitmap(bmp, newWidth, newHeight, false);
        // Delete the original
        bmp = null;

        bmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.egg);
        // Compute size of bitmap needed (suppose want width = 20% of screen width)
        newWidth = (int) (canvas.getWidth() * 0.3f);
        // What was the scaling factor to get to this?
        scaleFactor = (float) newWidth / bmp.getWidth();
        // Compute the new height
        newHeight = (int) (bmp.getHeight() * scaleFactor);
        // Scale it to a new size
        Assets.egg = Bitmap.createScaledBitmap(bmp, newWidth, newHeight, false);
        // Delete the original
        bmp = null;

        //Tama Walking 1
        bmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.wlak);
        // Compute size of bitmap needed (suppose want width = 20% of screen width)
        newWidth = (int) (canvas.getWidth() * 0.3f);
        // What was the scaling factor to get to this?
        scaleFactor = (float) newWidth / bmp.getWidth();
        // Compute the new height
        newHeight = (int) (bmp.getHeight() * scaleFactor);
        // Scale it to a new size
        Assets.walk1 = Bitmap.createScaledBitmap(bmp, newWidth, newHeight, false);
        // Delete the original
        bmp = null;

        //Tama Walking 2
        bmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.walk_second);
        // Compute size of bitmap needed (suppose want width = 20% of screen width)
        newWidth = (int) (canvas.getWidth() * 0.3f);
        // What was the scaling factor to get to this?
        scaleFactor = (float) newWidth / bmp.getWidth();
        // Compute the new height
        newHeight = (int) (bmp.getHeight() * scaleFactor);
        // Scale it to a new size
        Assets.wa1k2 = Bitmap.createScaledBitmap(bmp, newWidth, newHeight, false);
        // Delete the original
        bmp = null;

        //Tama Eating
        bmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.tama_eat);
        // Compute size of bitmap needed (suppose want width = 20% of screen width)
        newWidth = (int) (canvas.getWidth() * 0.3f);
        // What was the scaling factor to get to this?
        scaleFactor = (float) newWidth / bmp.getWidth();
        // Compute the new height
        newHeight = (int) (bmp.getHeight() * scaleFactor);
        // Scale it to a new size
        Assets.eat = Bitmap.createScaledBitmap(bmp, newWidth, newHeight, false);
        // Delete the original
        bmp = null;

        //Food 2
        bmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.food_second);
        // Compute size of bitmap needed (suppose want width = 20% of screen width)
        newWidth = (int) (canvas.getWidth() * 0.3f);
        // What was the scaling factor to get to this?
        scaleFactor = (float) newWidth / bmp.getWidth();
        // Compute the new height
        newHeight = (int) (bmp.getHeight() * scaleFactor);
        // Scale it to a new size
        Assets.food2 = Bitmap.createScaledBitmap(bmp, newWidth, newHeight, false);
        // Delete the original
        bmp = null;

    }

    // Load specific background screen
    private void loadBackground(Canvas canvas, int resId) {
        // Load background
        Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), resId);
        // Scale it to fill entire canvas
        Assets.background = Bitmap.createScaledBitmap(bmp, canvas.getWidth(), canvas.getHeight(), false);
        // Delete the original
        bmp = null;
    }

    //Rendering objects on screen
    private void render(final Canvas canvas) {
            final int i;
            int x;
            int y;

            if(Assets.state == Assets.GameState.READY){

                //Assets.mp.start();
                Assets.isPlaying=true;
                Assets.state = Assets.GameState.EGG;
            }
            ///////////////////////////////////             Egg State             //////////////////////////////////

            else if(Assets.state == Assets.GameState.EGG) {
                        // Load a special "getting ready screen"
                    loadBackground(canvas, R.drawable.gmae_background);
                   // loadBackground(canvas, R.drawable.game_background);
                    loadData(canvas);
                    //For BackGround Music
                    //Assets.mediaPlayer= MediaPlayer.create(context,R.raw.background_music);
                    //Assets.mediaPlayer.start();
                    //Draw Background Images
                    canvas.drawBitmap(Assets.background, 0, 0, null);

                    //Button for Frawing Buttons
                    Assets.Prefx = canvas.getWidth() - Assets.prefs.getWidth() - 100;
                    canvas.drawBitmap(Assets.prefs, canvas.getWidth() - Assets.prefs.getWidth() - 100, 100, null);
                    canvas.drawBitmap(Assets.food1, 100, 100, null);

                        if (counter == 0) {
                            counter=1;
                            Assets.GAMETIMER = System.nanoTime() / 1000000000f;
                        }
                        float currentTime = System.nanoTime() / 1000000000f;
                        if(((int)((currentTime -Assets.GAMETIMER)%3))==0){
                                System.out.println("Currrent Timer "+ (currentTime -Assets.GAMETIMER)%2);
                                // Draw the Egg/Tama at bottom of screen
                                Assets.x=(int)(Math.random()*canvas.getWidth());
                                if(Assets.x-Assets.egg.getWidth()<0){
                                    Assets.x=0;
                                }else{
                                    Assets.x = Assets.x - Assets.egg.getWidth();
                                }
                            canvas.drawBitmap(Assets.egg, Assets.x, canvas.getHeight() - Assets.egg.getHeight(), null);
                        }else{
                            canvas.drawBitmap(Assets.egg, Assets.x, canvas.getHeight() - Assets.egg.getHeight(), null);
                        }


                System.out.println("Currrent Timer atyare "+ ((int)((currentTime -Assets.GAMETIMER)%2)));
                    //5 second pose then go to the another state

                        if((currentTime -Assets.GAMETIMER) >=10){
                            counter=0;
                            Assets.soundpools.play(Assets.sound_EggCrack, 1, 1, 1, 0, 1);
                            Assets.state = Assets.GameState.HAPPY;
                            //TTS code
                            if(Assets.isTTS) {
                                if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                                    Assets.t1.speak("I am Happy Now!", TextToSpeech.QUEUE_FLUSH, null);
                                } else {
                                    Assets.t1.speak("I am Happy Now!", TextToSpeech.QUEUE_FLUSH, null, "1");
                                }
                            }
                            Assets.isHappySoundPlaying = true;

                            //if(Assets.isPlaying)
                              //  Assets.soundpool.play(Assets.sound_happy, 1, 1, 1, 0, 1);
                           // Assets.soundpool.play(Assets.sound_happy, 1, 1, 1, -1, 1);
                        }
                //////////////////////////    Happy State                  //////////////////////////////////
                }else if(Assets.state == Assets.GameState.HAPPY){
                    canvas.drawBitmap(Assets.background, 0, 0, null);
                    System.out.println("Happy sound loop"+ Assets.periodicaly++);
                    //Button for Frawing Buttons
                            canvas.drawBitmap(Assets.prefs, canvas.getWidth() - Assets.prefs.getWidth() - 100, 100, null);
                            canvas.drawBitmap(Assets.food1, 100, 100, null);

                    //Tama Running on the screen
                            Assets.tamaMove=false;
                            t= new tama(context,canvas);
                    //For Allow tama to Eat in Happy State
                        if(Assets.HappyStateTimer<20){
                            Assets.is_it_in_hungrystate=true;
                            if(Assets.is_hungry) {
                                if ((Assets.x + (4 * Assets.food1.getWidth())) < canvas.getWidth()) {
                                    t1 = new tamaEat(context, canvas);
                                    Assets.state = Assets.GameState.Eating;
                                    Assets.is_it_in_hungrystate = false;
                                }
                            }
                        }

                    if (counter == 0) {
                        counter=1;
                        Assets.GAMETIMER = System.nanoTime() / 1000000000f;
                    }
                        float currentTime = System.nanoTime() / 1000000000f;
                    if(((int)(currentTime -Assets.GAMETIMER)%4)==0){//For periodically play sound
                        if(Assets.isPlaying)
                            Assets.soundpools.play(Assets.sound_happy, 1, 1, 1, 0, 1);
                    }

                    if((currentTime -Assets.GAMETIMER) >=Assets.HappyStateTimer){

                        Assets.state = Assets.GameState.HUNGRY;
                        //TTS code
                        //Text to Speech
                        if(Assets.isTTS) {
                            if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                                Assets.t1.speak("I am Hungry Now!", TextToSpeech.QUEUE_FLUSH, null);
                            } else {
                                Assets.t1.speak("I am Hungy Now!", TextToSpeech.QUEUE_FLUSH, null, "1");
                            }
                        }
                        counter =0;
                        Assets.soundpools.stop(Assets.sound_happy);
                        if (Assets.isPlaying)
                        Assets.hungry_stream= Assets.soundpools.play(Assets.sound_hungry, 1, 1, 1, -1, 1);
                    }
                //////////////////////////    Hungry State                  //////////////////////////////////
                }else if(Assets.state == Assets.GameState.HUNGRY){
                        canvas.drawBitmap(Assets.background, 0, 0, null);
                        System.out.println("Reached into hungry state");
                        if(Assets.is_it_hungry) {
                            if (Assets.isPlaying)
                                Assets.soundpools.stop(Assets.sound_happy);
                                Assets.hungry_stream=Assets.soundpools.play(Assets.sound_hungry, 1, 1, 1, -1, 1);
                            Assets.is_it_hungry=false;
                        }
                        //Draw tama
                        canvas.drawBitmap(Assets.walk1, Assets.x, Assets.y, null);
                        //Button for Frawing Buttons
                        canvas.drawBitmap(Assets.prefs, canvas.getWidth() - Assets.prefs.getWidth() - 100, 100, null);
                        canvas.drawBitmap(Assets.food1, 100, 100, null);
                        Assets.tamaMove=true;
                        t= new tama(context,canvas);
                        Assets.is_it_in_hungrystate=true;
                        if(Assets.is_hungry) {
                            if((Assets.x+(4*Assets.food1.getWidth()))<canvas.getWidth()) {
                                t1 = new tamaEat(context, canvas);
                                Assets.state = Assets.GameState.Eating;
                                Assets.is_it_in_hungrystate=false;
                                Assets.is_it_hungry=true;//check whether it is first time or not
                            }
                        }

                //////////////////////////    Eating State                  //////////////////////////////////

                 }else if(Assets.state == Assets.GameState.Eating) {
                            canvas.drawBitmap(Assets.background, 0, 0, null);
                            System.out.println("Reached into Eating state");
                            //    Assets.soundpool.play(Assets.sound_hungry, 1, 1, 1, 0, 1);
                            canvas.drawBitmap(Assets.prefs, canvas.getWidth() - Assets.prefs.getWidth() - 100, 100, null);
                            canvas.drawBitmap(Assets.food1, 100, 100, null);
                            try{
                            t1.tamaEat();
                            }catch (Exception e){
                                System.out.println(e);
                            }

                            if (counter == 0) {
                                counter=1;
                                Assets.GAMETIMER = System.nanoTime() / 1000000000f;
                            }

                            float currentTime = System.nanoTime() / 1000000000f;
                            //5 second pose then go to the another state
                            if((currentTime -Assets.GAMETIMER) >=5){
                                //Assets.state = Assets.GameState.HAPPY;
                                Assets.state = Assets.GameState.HAPPY;
                                Assets.is_hungry = false;
                                //Assets.soundpool.play(Assets.sound_hungry, 1, 1, 1, 0, 1);
                                Assets.soundpools.stop(Assets.hungry_stream);
                                counter=0;
                                Assets.HappyStateTimer=15;
                                Assets.counter =0;
                                if(Assets.isPlaying)
                                Assets.soundpools.play(Assets.sound_happy, 1, 1, 1, 0, 1);
                            }
                }
        }
    }

