/*___________________________________________________________________
|
| File: GatorService
|
| Copyright 2015 Me
|__________________________________________________________________*/
package com.example.chintan.tamagotchigo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.MediaPlayer;
import android.util.Log;

/*___________________________________________________________________
|
| Class: GatorService
|
| Description: Service to handle audio media playback.  Waits for Intents
|	from TexclamationIntentReceiver (a broadcast receiver that receives
|	intents from the OS).
|__________________________________________________________________*/
public class GatorService extends MyIntentService
{
    // Intent actions that can be handled.  Constants created here for convenience.
    // What actually defines the actions the service can handle are the <action> tags in the <intent-filters> tag for the service in AndroidManifest.xml
    public static final String ACTION_START	= "com.examples.gator.START";
    public static final String ACTION_KILL_NOTIFICATION = "com.examples.gator.KILL_NOTIFICATION";

    // The ID we use for the notification (the onscreen alert that appears at the notification
    // area at the top of the screen as an icon -- and as text as well if the user expands the
    // notification area).
    final int NOTIFICATION_ID = 1;

    //AudioManager mAudioManager;
    NotificationManager mNotificationManager;

    Notification mNotification = null;

    /*___________________________________________________________________
	|
	| Function: GatorService (Constructor)
	|__________________________________________________________________*/
    public GatorService ()
    {
        super ("GatorService");
    }

    /*___________________________________________________________________
    |
    | Function: onCreate
    |__________________________________________________________________*/
    @Override
    public void onCreate ()
    {
        super.onCreate();

        // Get handles to system services
        mNotificationManager = (NotificationManager) getSystemService (NOTIFICATION_SERVICE);
        //mAudioManager        = (AudioManager)        getSystemService (AUDIO_SERVICE);

        Assets.serviceIsProcessing = false;
    }

    /*___________________________________________________________________
    |
    | Function: onHandleIntent
    |__________________________________________________________________*/
    @Override
    protected void onHandleIntent(Intent intent)
    {
        String action = intent.getAction ();
        //	Log.i (TAG, "onHandleIntent(): " + action);

        if 	    (action.equals(ACTION_START))				{ processStart ();	}
        else if (action.equals(ACTION_KILL_NOTIFICATION))	{ processStopNotify ();	}
    }

    /*___________________________________________________________________
    |
    | Function: onDestroy
    |__________________________________________________________________*/
    @Override
    public void onDestroy ()
    {
        // Service is being killed, so release resources used here, if any
        mNotificationManager = null;

        // Call super class version
        super.onDestroy();
    }

    /*___________________________________________________________________
    |
    | Function: processStart
    |__________________________________________________________________*/
    private void processStart ()
    {
        float curTime;

        // If service is already processing, then just exit
        if (Assets.serviceIsProcessing) {
            Log.i("ProjectLogging", "service is already processing");
            return;
        }

        // Busy wait until time when hungry is reached
        Assets.serviceIsProcessing = true;
        do {
            curTime = System.nanoTime() / 1000000000f;
        } while (curTime < Assets.timeWhenHungry);

        Intent intent = new Intent(this, SplashActivity.class);

        //PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Build the notification
        //  for the addAction re-use the same intent to keep the example short
        Notification n = new Notification.Builder(this)
                .setContentTitle("TamagotchiGo")
                .setContentText("Feed me!")
                .setSmallIcon(R.drawable.egg)
                .setContentIntent(pIntent)
                .setAutoCancel(true)
                .build();
        // Launch the notification
        mNotificationManager.notify(NOTIFICATION_ID, n);
    }

    /*___________________________________________________________________
    |
    | Function: processStopNotify
    |__________________________________________________________________*/
    private void processStopNotify ()
    {
        mNotificationManager.cancel(NOTIFICATION_ID);
    }
}
