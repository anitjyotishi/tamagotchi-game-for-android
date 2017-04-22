package com.example.chintan.tamagotchigo;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.nfc.Tag;
import android.speech.tts.TextToSpeech;

import java.util.Locale;

/**
 * Created by chintan24 on 7/26/2016.
 */
public class Assets {
    public static boolean tamaMove=false;
    public static Bitmap egg;
    public static Bitmap egg1;
    public static Bitmap walk1;
    public static Bitmap wa1k2;
    public static Bitmap background;
    public static Bitmap food1;
    public static Bitmap food2;
    public static Bitmap food3;
    public static Bitmap food4;
    public static Bitmap eat;
    public static float GAMETIMER;
    public static int HappyStateTimer=20;
    public static float TotalEatingTime=0;
    public static int foodCounter=0;
    public static boolean isPlaying=true;


    //TTs
    static TextToSpeech t1;
    static boolean isTTS=true;
    //For Buttons
    public static Bitmap prefs;
    public static int Prefx;
    //public static int foodx;

    //Used for tama class
    public static int loop=0;
    public static int x=0;
    public static int y;
    public static int counter=0;
    public static double speed=0;
    public static float animateTimer=0;
    public static boolean is_hungry=false;
    public static boolean is_it_in_hungrystate=false;

    //For sound load
    public static MediaPlayer mediaPlayer;
    //public static SoundPool soundpool;
    public static SoundPool soundpools;
    static int sound_EggCrack;
    static int sound_happy;
    static int sound_hungry;
    static boolean isHappySoundPlaying;

    //For Notification
    public static float timeWhenHungry;
    public static volatile boolean serviceIsProcessing;
    public static int periodicaly=0;
    public static boolean is_it_hungry=true;
    public static boolean timeHunger;

    public static MediaPlayer mp;
    public static boolean noNotify=true;
    public static int hungry_stream;

    // States of the Game Screen
    enum GameState {
        EGG,
        HAPPY,
        HUNGRY,
        Eating,
        READY,
    };
    static GameState state= GameState.EGG;

    //Used for tama class states
    enum TamaState {
        Load,
        Move,
    };
    static TamaState tstate= TamaState.Load;
}
